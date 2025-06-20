package com.insert.ioj.domain.submission.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.repository.SubmissionRepository;
import com.insert.ioj.domain.submission.presentation.dto.req.SubmissionRequest;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.global.feign.kubernetes.KubernetesClient;
import com.insert.ioj.global.feign.kubernetes.dto.req.KubernetesSubmissionRequest;
import com.insert.ioj.infra.status.VerificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubmissionService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final SubmissionRepository submissionRepository;
    private final KubernetesClient kubernetesClient;
    private final UserFacade userFacade;

    @Transactional
    public UUID create(SubmissionRequest request) throws IOException {
        Problem problem = problemRepository.findById(request.problemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();

        Submission submission = new Submission(
            request.language(), request.sourcecode(), user, problem
        );

        Execution execution = ExecutionFactory.createExecution(
            submission.getId().toString(),
            request.sourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.language()
        );
        execution.createExecutionDirectory();

        kubernetesClient.kubernetesSubmission(
            new KubernetesSubmissionRequest(
                execution.getId(), execution.getMemoryLimit(), execution.getTimeLimit(), execution.getLanguage()
            )
        );

        submissionRepository.save(submission);
        return submission.getId();
    }

    @Transactional
    public void complete(String id) throws IOException {
        Submission submission = submissionRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(submission.getProblem())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        Verdict verdict = VerificationUtil.verify(submission, testcases);
        submission.updateVerdict(verdict);
    }

    @Transactional
    public void completeCompile(String id) {
        Submission submission = submissionRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));

        submission.updateVerdict(Verdict.COMPILATION_ERROR);
    }
}
