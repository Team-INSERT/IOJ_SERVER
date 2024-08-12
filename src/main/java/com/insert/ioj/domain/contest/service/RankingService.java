package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ListRankResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ProblemStatusResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.RankingResponse;
import com.insert.ioj.domain.problem.facade.ProblemFacade;
import com.insert.ioj.domain.problem.presentation.dto.res.ProblemStatusDto;
import com.insert.ioj.domain.solveContest.domain.repository.CustomSolveContestRepository;
import com.insert.ioj.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankingService {
    private final ContestFacade contestFacade;
    private final CustomSolveContestRepository customSolveContestRepository;
    private final ProblemFacade problemFacade;

    public List<RankingResponse> execute(Long id) {
        Contest contest = contestFacade.getContest(id);
        List<ListRankResponse> rankingUser = customSolveContestRepository.getRankingUser(contest);

        List<RankingResponse> rankingResponses = new ArrayList<>();
        for (ListRankResponse rank : rankingUser) {
            User user = rank.getUser();
            List<ProblemStatusDto> userProblemStatuses = problemFacade.getProblemStatuses(contest, user);
            List<ProblemStatusResponse> problemStatuses = getProblemStatusResponses(userProblemStatuses, contest);

            rankingResponses.add(new RankingResponse(user.getNickname(), problemStatuses));
        }
        return rankingResponses;
    }

    private List<ProblemStatusResponse> getProblemStatusResponses(List<ProblemStatusDto> userProblemStatuses,
                                                                  Contest contest) {
        List<ProblemStatusResponse> problemStatuses = new ArrayList<>();
        for (ProblemStatusDto problemStatus : userProblemStatuses) {
            LocalDateTime startTime = contest.getStartTime();
            Long penalty = penalty(problemStatus.getSolveTime(), startTime);
            problemStatuses.add(new ProblemStatusResponse(problemStatus.getStatus(), penalty));
        }
        return problemStatuses;
    }

    private Long penalty(LocalDateTime solveTime, LocalDateTime startTime) {
        if (solveTime != null) {
            Duration duration = Duration.between(startTime, solveTime);
            return duration.toMinutes();
        }
        return null;
    }
}
