package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problem.facade.ProblemFacade;
import com.insert.ioj.domain.problem.presentation.dto.res.ProblemStatusDto;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetContestService {
    private final UserFacade userFacade;
    private final ContestFacade contestFacade;
    private final ProblemFacade problemFacade;

    public ContestResponse execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(contestId);

        contest.isNotStarted();
        contest.checkRole(user.getAuthority());

        List<ProblemStatusDto> problemStatuses = problemFacade.getProblemStatuses(contest, user);

        List<ListContestProblemResponse> contestProblem = new ArrayList<>();
        for (ProblemStatusDto problemStatus : problemStatuses) {
            contestProblem.add(new ListContestProblemResponse(problemStatus));
        }

        return new ContestResponse(contest, contestProblem);
    }
}
