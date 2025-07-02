package com.insert.ioj.domain.submission.facade;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.submission.facade.dto.TestcaseCacheDto;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Cacheable(cacheNames = "contests", key = "#id")
    public Optional<Contest> findContestById(Long id) {
        return contestRepository.findById(id);
    }

    @Cacheable(cacheNames = "testcases", key = "#id")
    public TestcaseCacheDto[] findTestcasesById(Long id) {
        List<Testcase> testcases = testcaseRepository.findAllByProblem_Id(id);
        return testcases.stream()
            .map(TestcaseCacheDto::new)
            .toArray(TestcaseCacheDto[]::new);
    }

    @Cacheable(cacheNames = "problems", key = "#id")
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findById(id);
    }

    @Cacheable(cacheNames = "users", key = "#id")
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
