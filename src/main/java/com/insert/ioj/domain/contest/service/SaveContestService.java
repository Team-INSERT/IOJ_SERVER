package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.contest.presentation.dto.req.SaveContestRequest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.problemContest.domain.ProblemContest;
import com.insert.ioj.domain.problem.problemContest.domain.repository.ProblemContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveContestService {
    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final ProblemContestRepository problemContestRepository;

    @Transactional
    public Long execute(SaveContestRequest request) {
        Contest contest = contestRepository.save(request.toEntity());
        List<Problem> problems = problemRepository.findAllById(request.getProblems());
        for(Problem problem: problems) {
            problemContestRepository.save(
                new ProblemContest(problem, contest)
            );
        }
        return contest.getId();
    }
}
