package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ListRankResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ProblemStatusResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.RankingResponse;
import com.insert.ioj.domain.problem.problem.facade.ProblemFacade;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ProblemStatusDto;
import com.insert.ioj.domain.problem.problemContest.domain.repository.ProblemContestRepository;
import com.insert.ioj.domain.problemscore.domain.repository.ProblemScoreRepository;
import com.insert.ioj.domain.ranking.domain.Ranking;
import com.insert.ioj.domain.ranking.domain.repository.RankingRepository;
import com.insert.ioj.domain.ranking.presentation.dto.response.RankResponse;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.ProblemOrderElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.RankElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.SubmissionDetailElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.UserSubmissionElement;
import com.insert.ioj.domain.solve.contest.repository.CustomSolveContestRepository;
import com.insert.ioj.domain.submission.facade.EntityFacade;
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
    private final EntityFacade entityFacade;
    private final ProblemContestRepository problemContestRepository;
    private final RankingRepository rankingRepository;
    private final ProblemScoreRepository problemScoreRepository;

    public RankResponse ranking(Long contestId) {
        List<Ranking> rankings = rankingRepository.getRankings(contestId);

        List<ProblemOrderElement> problemOrders = problemContestRepository.findAllByContest_Id(contestId).stream()
            .map(ProblemOrderElement::from)
            .toList();

        List<RankElement> rankElements = rankings.stream()
                .map(RankElement::toEntity)
                .toList();

        List<UserSubmissionElement> submissions = rankElements.stream()
            .map(it -> {
                    List<SubmissionDetailElement> problems = problemScoreRepository.findByContestAndUser(contestId, it.userId()).stream()
                        .map(SubmissionDetailElement::from)
                        .toList();
                    return new UserSubmissionElement(problems, it.userId());
                }
            ).toList();

        return new RankResponse(problemOrders, rankElements, submissions);
    }







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
