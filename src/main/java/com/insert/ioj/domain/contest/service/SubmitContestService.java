package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.req.SubmitContestRequest;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.presentation.dto.res.TestcaseResult;
import com.insert.ioj.domain.execution.service.ExecutionService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.solveContest.domain.SolveContest;
import com.insert.ioj.domain.solveContest.domain.repository.SolveContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.docker.DockerUtil;
import com.insert.ioj.infra.status.StatusUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitContestService {
    private final TestcaseRepository testcaseRepository;
    private final SolveContestRepository solveContestRepository;
    private final ContestFacade contestFacade;
    private final ProblemRepository problemRepository;
    private final UserFacade userFacade;
    private final ExecutionService executionService;

    @Transactional
    public Verdict execute(SubmitContestRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(request.getContestId());

        contest.checkRole(user.getAuthority());

        Execution execution = ExecutionFactory.createExecution(
            request.getSourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.getLanguage()
        );

        Verdict verdict = Verdict.ACCEPTED;
        createEnvironmentAndBuild(execution);
        for (Testcase testcase : testcases) {
            TestcaseResult testcaseResult = getTestcaseResult(execution, testcase);

            verdict = testcaseResult.getVerdict();
            if (testcaseResult.getVerdict() != Verdict.ACCEPTED)
                break;
        }
        solveContestRepository.save(new SolveContest(
            user, contest, problem, execution.getSourcecode(), verdict, execution.getLanguage()));
        deleteEnvironment(execution);

        return verdict;
    }

    private TestcaseResult getTestcaseResult(Execution execution, Testcase testcase) {
        try {
            ProcessOutput processOutput = executionService.run(execution, testcase.getId().toString());

            boolean ans = testcase.getOutput().equals(processOutput.getStdOut());
            Verdict verdict = StatusUtil.statusResponse(processOutput.getStatus(), ans);

            return new TestcaseResult(
                verdict,
                processOutput.getStdOut(),
                processOutput.getStdErr(),
                testcase,
                processOutput.getExecutionDuration()
            );
        } catch (Exception e) {
            return new TestcaseResult(
                Verdict.TIME_LIMIT_EXCEEDED,
                "",
                "실행이 시간 제한을 초과했습니다.",
                testcase,
                execution.getTimeLimit() + 1
            );
        }
    }

    private void createEnvironmentAndBuild(Execution execution) {
        buildExecutionEnvironment(execution);
        execution.createEntrypointFiles();

        String dockerfilePath = execution.getPath() + "/" + FileConstants.DOCKER_FILE_NAME;
        DockerUtil.buildImage(dockerfilePath, execution.getImageName(), execution.getPath());
    }

    private void buildExecutionEnvironment(Execution execution) {
        try {
            execution.createExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEnvironment(Execution execution) {
        try {
            execution.deleteExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
