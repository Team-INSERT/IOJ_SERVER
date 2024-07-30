package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestAdminResponse;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemContest.domain.repository.ProblemContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListContestAdminService {
    private final ContestRepository contestRepository;
    private final ProblemContestRepository problemContestRepository;

    @Transactional(readOnly = true)
    public List<ListContestAdminResponse> execute() {
        List<Contest> contests = contestRepository.findAllByEndTimeAfter(LocalDateTime.now());
        return contests.stream()
            .map(contest -> {
                List<Problem> problems = problemContestRepository.getProblems(contest);
                return new ListContestAdminResponse(contest, problems);
            }).toList();
    }
}
