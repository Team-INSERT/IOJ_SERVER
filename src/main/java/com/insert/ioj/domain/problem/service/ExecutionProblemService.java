package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.presentation.dto.res.TestcaseResult;
import com.insert.ioj.domain.execution.service.ExecutionService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.req.ExecutionProblemRequest;
import com.insert.ioj.domain.solve.domain.repository.SolveRepository;
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
public class ExecutionProblemService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final ExecutionService executionService;
    private final SolveRepository solveRepository;
    private final UserFacade userFacade;

    @Transactional
    public Verdict execute(ExecutionProblemRequest request) {
        Problem problem = problemRepository.findById(request.getId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();

        Execution execution = ExecutionFactory.createExecution(
            request.getSourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.getLanguage()
        );
        
        createEnvironmentAndBuild(execution);
        for (Testcase testcase : testcases) {
            TestcaseResult testcaseResult = getTestcaseResult(execution, testcase);
            solveRepository.save(testcaseResult.toEntity(
                user, problem, execution.getSourcecode(), testcaseResult.getVerdict(), execution.getLanguage()));

            if (testcaseResult.getVerdict() != Verdict.ACCEPTED) {
                deleteEnvironment(execution);
                return testcaseResult.getVerdict();
            }
        }
        deleteEnvironment(execution);

        return Verdict.ACCEPTED;
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
