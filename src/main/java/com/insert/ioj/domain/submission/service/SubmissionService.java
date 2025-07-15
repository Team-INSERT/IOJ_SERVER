package com.insert.ioj.domain.submission.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.insert.ioj.domain.problemscore.domain.repository.ProblemScoreRepository;
import com.insert.ioj.domain.ranking.domain.Ranking;
import com.insert.ioj.domain.ranking.domain.repository.RankingRepository;
import com.insert.ioj.domain.submission.domain.Artifact;
import com.insert.ioj.domain.submission.domain.ContestSubmission;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.TestcaseSubmission;
import com.insert.ioj.domain.submission.domain.repository.ArtifactRepository;
import com.insert.ioj.domain.submission.domain.repository.ContestSubmissionRepository;
import com.insert.ioj.domain.submission.domain.repository.SubmissionRepository;
import com.insert.ioj.domain.submission.domain.repository.TestcaseSubmissionRepository;
import com.insert.ioj.domain.submission.facade.EntityFacade;
import com.insert.ioj.domain.submission.presentation.dto.req.GetSubmissionsRequest;
import com.insert.ioj.domain.submission.presentation.dto.req.SubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.req.TestcasesSubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.req.TestcasesSubmissionRequest.TestcaseResultDto;
import com.insert.ioj.domain.submission.presentation.dto.res.SubmissionResponse;
import com.insert.ioj.domain.submission.presentation.dto.res.SubmissionsResponse;
import com.insert.ioj.domain.submission.presentation.dto.res.TestcaseSubmissionStatusResponse;
import com.insert.ioj.domain.subtask.domain.Subtask;
import com.insert.ioj.domain.subtask.domain.SubtaskResult;
import com.insert.ioj.domain.subtask.domain.repository.SubtaskRepository;
import com.insert.ioj.domain.subtask.domain.repository.SubtaskResultRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.repository.UserRepository;
import com.insert.ioj.domain.user.domain.type.Authority;
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
    private final TestcaseRepository testcaseRepository;
    @Value("${volume.path}")
    private String volumePath;

    private final UserFacade userFacade;
    private final EntityFacade entityFacade;
    private final UserRepository userRepository;
    private final ArtifactRepository artifactRepository;
    private final SubmissionRepository submissionRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final TestcaseSubmissionRepository testcaseSubmissionRepository;
    private final ApplicationEventPublisher publisher;
    private final SubtaskRepository subtaskRepository;
    private final SubtaskResultRepository subtaskResultRepository;
    private final ProblemScoreRepository problemScoreRepository;
    private final RankingRepository rankingRepository;

    @Transactional
    public SubmissionResponse submissionStatus(UUID id) {
        ContestSubmission submission = contestSubmissionRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));

        List<SubtaskResult> subtaskResults = subtaskResultRepository.findAllBySubmission(submission);
        List<Artifact> artifacts = artifactRepository.findAllBySubmission(submission);

        List<String> details = new ArrayList<>();
        int startIndex = 0;

        String compilationDetail = null;
        if (submission.getVerdict() == Verdict.COMPILATION_ERROR) {
            compilationDetail = artifacts.get(0).getStderr();
        }

        for (SubtaskResult it : subtaskResults) {
            switch (it.getVerdict()) {
                case ACCEPTED:
                    details.add(null);
                    startIndex += it.getTotalTestcases();
                    break;

                case RUNTIME_ERROR:
                case PARTIAL:
                case WRONG_ANSWER:
                case OUT_OF_MEMORY:
                case TIME_LIMIT_EXCEEDED:
                    int testcaseCount = it.getTotalTestcases();
                    List<Artifact> subtaskArtifacts = artifacts.subList(startIndex, startIndex + testcaseCount);

                    String detail = null;
                    for (int i = 0; i < subtaskArtifacts.size(); i++) {
                        if (subtaskArtifacts.get(i).getVerdict() == Verdict.RUNTIME_ERROR) {
                            detail = subtaskArtifacts.get(i).getStderr();
                            break;
                        }
                        if (subtaskArtifacts.get(i).getVerdict() != Verdict.ACCEPTED) {
                            detail = String.valueOf(i+1);
                            break;
                        }
                    }
                    details.add(detail);
                    startIndex += testcaseCount;
                    break;
            }
        }

        updateOrCreateProblemScore(submission);

        return SubmissionResponse.of(submission, subtaskResults, details, compilationDetail);
    }

    @Transactional(readOnly = true)
    public List<SubmissionsResponse> submissionsStatus(GetSubmissionsRequest request) {
        Long userId = userFacade.getCurrentUserId();
        List<ContestSubmission> submissions = contestSubmissionRepository.findByUserIdAndContestIdAndProblemId(
            userId, request.contestId(), request.ProblemId()
        );

        return submissions.stream()
            .map(SubmissionsResponse::toEntity)
            .toList();
    }

    @Transactional
    public List<TestcaseSubmissionStatusResponse> testcaseSubmissionStatus(UUID id) {
        Submission submission = submissionRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));
        List<Artifact> artifacts = artifactRepository.findAllBySubmission(submission);
        List<TestcaseSubmission> testcaseSubmissions = testcaseSubmissionRepository.findAllBySubmission(submission);

        List<TestcaseSubmissionStatusResponse> response = new ArrayList<>();
        for (int i = 0; i < artifacts.size(); i++) {
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
                String output = (artifact.getVerdict() == Verdict.ACCEPTED || artifact.getVerdict() == Verdict.WRONG_ANSWER)
                    ? artifact.getStdout()
                    : artifact.getStderr();

                response.add(
                    new TestcaseSubmissionStatusResponse(
                        testcaseSubmission.getInput(), output,
                        testcaseSubmission.getExpectedOutput(), artifact.getVerdict()
                    )
                );
                testcaseSubmission.updateVerdict(artifact.getVerdict());
            }
        }

        return response;
    }

    @Transactional
    public UUID testcasesSubmission(TestcasesSubmissionRequest request) throws IOException {
        Problem problem = entityFacade.getProblemById(request.problemId());
        Long userId = userFacade.getCurrentUserId();
        User user = userRepository.getReferenceById(userId);

        Submission submission = new Submission(
            request.language(), request.sourcecode(), user, problem
        );
        submissionRepository.save(submission);

        List<TestcaseSubmission> submissions = new ArrayList<>();
        for (int i = 0; i < request.testcaseResultDto().size(); i++) {
            TestcaseResultDto dto = request.testcaseResultDto().get(i);

            submissions.add(
                new TestcaseSubmission(
                    i,
                    dto.input(),
                    dto.expectedOutput() + "\n",
                    submission
                )
            );
        }
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
        Problem problem = entityFacade.getProblemById(request.problemId());
        Long userId = userFacade.getCurrentUserId();
        User user = userRepository.getReferenceById(userId);
        Authority userAuthority = userFacade.getCurrentUserAuthority();
        Contest contest = entityFacade.getContestById(request.contestId());

        contest.isNotStarted();
        contest.isFinished();
        contest.checkRole(userAuthority);

        existsCorrectProblem(contest, userId, problem);

        ContestSubmission submission = new ContestSubmission(
            request.language(), request.sourcecode(), user, problem, contest
        );

        contestSubmissionRepository.save(submission);

        List<Subtask> subtasks = subtaskRepository.findAllByProblem_Id(problem.getId());
        List<Testcase> testcases = testcaseRepository.findAllBySubtasksASC(subtasks);

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

        Verdict verdict;
        if ("compile".equals(status)) {
            String result = FileUtil.readFile(
                volumePath + "/%s/results/".formatted(id) + FileConstants.COMPILE_STDERR_FILE);
            result = result.replace("vol/" + id + "/", "");

            artifactRepository.save(
                new Artifact(null, result, null, submission, Verdict.COMPILATION_ERROR)
            );

            submission.updateVerdict(Verdict.COMPILATION_ERROR);
            return;
        }

        List<TestcaseSubmission> testcaseSubmissions = testcaseSubmissionRepository.findAllBySubmission(submission);
        Problem problem = submission.getProblem();
        List<Testcase> testcases;
        List<Artifact> artifacts;

        if (testcaseSubmissions.isEmpty()) {
            List<Subtask> subtasks = subtaskRepository.findAllByProblem_Id(problem.getId());
            testcases = testcaseRepository.findAllBySubtasksASC(subtasks);

            artifacts = toArtifacts(submission, testcases.size());
            artifacts = VerificationUtil.evaluateTestcases(artifacts, testcases);
            artifactRepository.saveAll(artifacts);

            int startIndex = 0;
            for (Subtask subtask : subtasks) {
                int testcaseCount = subtask.getTotalTestcases();
                List<Artifact> subtaskArtifacts = artifacts.subList(startIndex, startIndex + testcaseCount);

                int passedTestcases = (int) subtaskArtifacts.stream()
                    .filter(artifact -> artifact.getVerdict() == Verdict.ACCEPTED)
                    .count();

                double maxExecutionTime = subtaskArtifacts.stream()
                    .mapToDouble(Artifact::getExecutionTime)
                    .max()
                    .orElse(-1.0);

                int maxMemoryUsed = subtaskArtifacts.stream()
                    .mapToInt(Artifact::getMemoryUsage)
                    .max()
                    .orElse(-1);

                Verdict subtaskVerdict = VerificationUtil.verify(subtaskArtifacts);

                SubtaskResult subtaskResult = new SubtaskResult(
                    passedTestcases, maxExecutionTime, maxMemoryUsed, subtaskVerdict, submission, subtask
                );
                subtaskResultRepository.save(subtaskResult);
                submission.updateTotalScore(subtaskResult.getScore());

                startIndex += testcaseCount;
            }
        } else {
            testcases = testcaseSubmissions.stream()
                .map(TestcaseSubmission::toTestcase)
                .toList();

            artifacts = toArtifacts(submission, testcaseSubmissions.size());
            artifacts = VerificationUtil.evaluateTestcases(artifacts, testcases);
            artifactRepository.saveAll(artifacts);
        }

        verdict = VerificationUtil.verify(artifacts);
        if (submission.getTotalScore() != 0 && verdict != Verdict.ACCEPTED) {
            verdict = Verdict.PARTIAL;
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

    private void existsCorrectProblem(Contest contest, Long userId, Problem problem) {
        Boolean isCorrect = contestSubmissionRepository.existsByCorrectProblem(contest, userId, problem);
        if (isCorrect)
            throw new IojException(ErrorCode.ALREADY_SOLVED_PROBLEM);
    }

    private void updateOrCreateProblemScore(ContestSubmission submission) {
        problemScoreRepository.findByProblemIdAndContestAndUser(submission.getProblem().getId(), submission.getContest(), submission.getUser())
            .ifPresentOrElse(
                score -> {
                    if (score.getScore() < submission.getTotalScore()) {
                        score.update(submission.getTotalScore(), submission.getVerdict());
                        rankingRepository.findByContestAndUser(submission.getContest(), submission.getUser())
                            .ifPresent(
                                ranking -> ranking.update(submission.getTotalScore(), submission.getCreatedAt())
                            );
                    }
                },
                () -> createNewProblemScore(submission)
            );
    }

    private void createNewProblemScore(ContestSubmission submission) {
        ProblemScore problemScore = new ProblemScore(submission.getTotalScore(), submission.getVerdict(), submission.getProblem().getId(), submission.getContest(), submission.getUser());
        problemScoreRepository.save(problemScore);
        rankingRepository.findByContestAndUser(submission.getContest(), submission.getUser())
            .ifPresentOrElse(
                ranking -> {},
                () -> createNewRanking(submission)
            );
    }

    private void createNewRanking(ContestSubmission submission) {
        Ranking ranking = new Ranking(submission.getTotalScore(), submission.getCreatedAt(), submission.getContest(), submission.getUser());
        rankingRepository.save(ranking);
    }
}
