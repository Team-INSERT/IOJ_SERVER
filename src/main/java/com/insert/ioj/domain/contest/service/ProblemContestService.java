package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problemContest.domain.repository.CustomCompetitionContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemContestService {
    private final UserFacade userFacade;
    private final ContestFacade contestFacade;
    private final CustomCompetitionContestRepository customProblemContestRepository;

    public List<ListContestProblemResponse> execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(contestId);
        contest.checkRole(user.getAuthority());
        return customProblemContestRepository.getContestProblemsWithStatus(contest, user);
    }
}
