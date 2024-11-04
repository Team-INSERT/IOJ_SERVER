package com.insert.ioj.domain.problem.problem.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.presentation.dto.res.TestcaseResult;
import com.insert.ioj.domain.execution.service.ExecutionService;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.ExecutionProblemRequest;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.TestcasesResponse;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.docker.DockerUtil;
import com.insert.ioj.infra.testcase.TestcaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RequiredArgsConstructor
@Service
public class VerifyTestcasesService {
    private final TestcaseRepository testcaseRepository;
    private final ProblemRepository problemRepository;
    private final ExecutionService executionService;

    @Transactional
    public List<TestcasesResponse> execute(ExecutionProblemRequest request) {
        Problem problem = problemRepository.findById(request.getId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblemAndExampleIsTrue(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        Execution execution = ExecutionFactory.createExecution(
            request.getSourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.getLanguage()
        );

        createEnvironmentAndBuild(execution);

        List<TestcasesResponse> testcasesResponses = new ArrayList<>();
        ListIterator<Testcase> it = testcases.listIterator();
        while (it.hasNext()) {
            int index = it.nextIndex();
            Testcase testcase = it.next();
            TestcaseResult testcaseResult = getTestcaseResult(execution, testcase);

            switch (testcaseResult.getVerdict()) {
                case ACCEPTED, WRONG_ANSWER ->
                    testcasesResponses.add(new TestcasesResponse(
                        index, testcase, testcaseResult.getOutput(), testcaseResult.getVerdict()));
                default ->
                    testcasesResponses.add(new TestcasesResponse(
                        index, testcase, testcaseResult.getError(), testcaseResult.getVerdict()));
            }
        }
        DockerUtil.deleteImage(execution.getImageName());
        deleteEnvironment(execution);

        return testcasesResponses;
    }

    private TestcaseResult getTestcaseResult(Execution execution, Testcase testcase) {
        try {
            ProcessOutput processOutput = executionService.run(execution, testcase.getId().toString());
            return TestcaseUtil.testcaseResponse(processOutput, testcase);
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
