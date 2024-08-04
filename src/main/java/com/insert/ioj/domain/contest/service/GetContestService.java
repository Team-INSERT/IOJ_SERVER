package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problemContest.domain.repository.CustomCompetitionContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetContestService {
    private final UserFacade userFacade;
    private final ContestFacade contestFacade;
    private final CustomCompetitionContestRepository customProblemContestRepository;

    public ContestResponse execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(contestId);
        contest.checkRole(user.getAuthority());

        List<ListContestProblemResponse> problems =
            customProblemContestRepository.getContestProblemsWithStatus(contest, user);

        return new ContestResponse(contest, problems);
    }
}
