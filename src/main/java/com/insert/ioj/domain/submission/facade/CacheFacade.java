package com.insert.ioj.domain.submission.facade;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CacheFacade {
    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;
    private final TestcaseRepository testcaseRepository;
    private final ProblemRepository problemRepository;

    @Cacheable(cacheNames = "contests", key = "#id")
    public Optional<Contest> findContestById(Long id) {
        return contestRepository.findById(id);
    }

    @Cacheable(cacheNames = "submissions", key = "#id")
    public Optional<Submission> findSubmissionById(UUID id) {
        return submissionRepository.findById(id);
    }

    @Cacheable(cacheNames = "testcases", key = "#problem.id")
    public Optional<List<Testcase>> findTestcasesById(Problem problem) {
        return testcaseRepository.findAllByProblem(problem);
    }

    @Cacheable(cacheNames = "problems", key = "#id")
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findById(id);
    }
}
