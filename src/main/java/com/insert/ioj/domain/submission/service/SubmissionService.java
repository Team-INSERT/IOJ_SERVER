package com.insert.ioj.domain.submission.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.submission.domain.Artifact;
import com.insert.ioj.domain.submission.domain.ContestSubmission;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.TestcaseSubmission;
import com.insert.ioj.domain.submission.domain.repository.ArtifactRepository;
import com.insert.ioj.domain.submission.domain.repository.ContestSubmissionRepository;
import com.insert.ioj.domain.submission.domain.repository.SubmissionRepository;
import com.insert.ioj.domain.submission.domain.repository.TestcaseSubmissionRepository;
import com.insert.ioj.domain.submission.presentation.dto.req.SubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.req.TestcasesSubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.res.TestcaseSubmissionStatusResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.infra.file.FileUtil;
import com.insert.ioj.infra.runner.RunnerUtil;
import com.insert.ioj.infra.status.VerificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubmissionService {
    @Value("${volume.path}")
    private String volumePath;

    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final SubmissionRepository submissionRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final TestcaseSubmissionRepository testcaseSubmissionRepository;
    private final ArtifactRepository artifactRepository;
    private final ContestFacade contestFacade;
    private final UserFacade userFacade;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true)
    public Verdict submissionStatus(UUID id) {
        Submission submission = contestSubmissionRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));

        return submission.getVerdict();
    }

    @Transactional
    public List<TestcaseSubmissionStatusResponse> testcaseSubmissionStatus(UUID id) {
        Submission submission = submissionRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));

        List<Artifact> artifacts = artifactRepository.findAllBySubmission(submission);
        List<TestcaseSubmission> testcaseSubmissions = testcaseSubmissionRepository.findAllBySubmission(submission);

        List<TestcaseSubmissionStatusResponse> response = new ArrayList<>();
        for (int i=0; i < artifacts.size(); i++) {
            Artifact artifact = artifacts.get(i);
            TestcaseSubmission testcaseSubmission = testcaseSubmissions.get(i);

            if (submission.getVerdict() == Verdict.COMPILATION_ERROR) {
                response.add(
                    new TestcaseSubmissionStatusResponse(
                        testcaseSubmission.getInput(), artifact.getStderr(),
                        testcaseSubmission.getExpectedOutput(), Verdict.COMPILATION_ERROR
                    )
                );

                testcaseSubmission.updateVerdict(Verdict.COMPILATION_ERROR);
            } else {
                Verdict verdict = VerificationUtil.evaluateTestcase(
                    artifact, testcaseSubmission.toTestcase()
                );
                String output = (verdict == Verdict.ACCEPTED || verdict == Verdict.WRONG_ANSWER)
                    ? artifact.getStdout()
                    : artifact.getStderr();

                response.add(
                    new TestcaseSubmissionStatusResponse(
                        testcaseSubmission.getInput(), output,
                        testcaseSubmission.getExpectedOutput(), verdict
                    )
                );
                testcaseSubmission.updateVerdict(verdict);
            }
        }

        return response;
    }

    @Transactional
    public UUID testcasesSubmission(TestcasesSubmissionRequest request) throws IOException {
        Problem problem = problemRepository.findById(request.problemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();

        Submission submission = new Submission(
            request.language(), request.sourcecode(), user, problem
        );
        submissionRepository.save(submission);

        List<TestcaseSubmission> submissions = request.testcaseResultDto().stream()
            .map(dto -> new TestcaseSubmission(
                dto.input(),
                dto.expectedOutput(),
                submission
            ))
            .collect(Collectors.toList());
        testcaseSubmissionRepository.saveAll(submissions);

        List<Testcase> testcases = submissions.stream()
            .map(TestcaseSubmission::toTestcase)
            .collect(Collectors.toList());

        Execution execution = ExecutionFactory.createExecution(
            submission.getId().toString(),
            request.sourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.language(),
            volumePath
        );
        execution.createExecutionDirectory();

        publisher.publishEvent(execution);

        return submission.getId();
    }

    @Transactional
    public UUID create(SubmissionRequest request) throws IOException {
        Problem problem = problemRepository.findById(request.problemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(request.contestId());

        contest.isNotStarted();
        contest.isFinished();
        contest.checkRole(user.getAuthority());

        existsCorrectProblem(contest, user, problem);

        ContestSubmission submission = new ContestSubmission(
            request.language(), request.sourcecode(), user, problem, contest
        );

        contestSubmissionRepository.save(submission);

        Execution execution = ExecutionFactory.createExecution(
            submission.getId().toString(),
            request.sourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.language(),
            volumePath
        );
        execution.createExecutionDirectory();

        publisher.publishEvent(execution);

        return submission.getId();
    }

    @Transactional
    public void complete(String id, String status) throws IOException {
        Submission submission = submissionRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));
        Problem problem = submission.getProblem();
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        Verdict verdict;
        if ("compile".equals(status)) {
            String result = FileUtil.readFile(
                volumePath+"/%s/results/".formatted(id) + FileConstants.COMPILE_STDERR_FILE);
            result = result.replace("vol/"+id+"/", "");

            artifactRepository.save(
                new Artifact(null, result, null, submission)
            );

            verdict = Verdict.COMPILATION_ERROR;
        } else {
            List<Artifact> artifacts = toArtifacts(submission, testcases.size());
            artifactRepository.saveAll(artifacts);

            verdict = VerificationUtil.verify(artifacts, testcases);
        }

        submission.updateVerdict(verdict);
    }

    private List<Artifact> toArtifacts(Submission submission, int testcaseCnt) throws IOException {
        List<String> stdoutResults = RunnerUtil.readArtifacts(
            submission.getId(), testcaseCnt, FileConstants.STDOUT_FILE, volumePath);
        List<String> stderrResults = RunnerUtil.readArtifacts(
            submission.getId(), testcaseCnt, FileConstants.STDERR_FILE, volumePath);
        List<String> metaResults = RunnerUtil.readArtifacts(
            submission.getId(), testcaseCnt, FileConstants.META_FILE, volumePath);

        List<Artifact> artifacts = new ArrayList<>();
        for (int i = 0; i < testcaseCnt; i++) {
            artifacts.add(
                new Artifact(
                    stdoutResults.get(i),
                    stderrResults.get(i),
                    metaResults.get(i),
                    submission
                )
            );
        }

        return artifacts;
    }

    private void existsCorrectProblem(Contest contest, User user, Problem problem) {
        Boolean isCorrect = contestSubmissionRepository.existsByCorrectProblem(contest, user, problem);
        if (isCorrect)
            throw new IojException(ErrorCode.ALREADY_SOLVED_PROBLEM);
    }
}
