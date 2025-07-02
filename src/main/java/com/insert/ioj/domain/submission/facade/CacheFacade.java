package com.insert.ioj.domain.submission.facade;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CacheFacade {
    private final ContestRepository contestRepository;
    private final TestcaseRepository testcaseRepository;
    private final ProblemRepository problemRepository;

    @Cacheable(cacheNames = "contests", key = "#id")
    public Optional<Contest> findContestById(Long id) {
        System.out.println("DB hit");
        return contestRepository.findById(id);
    }

    @Cacheable(cacheNames = "testcases", key = "#id")
    public List<Testcase> findTestcasesById(Long id) {
        return testcaseRepository.findAllByProblem_Id(id);
    }

    @Cacheable(cacheNames = "problems", key = "#id")
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findById(id);
    }
}
