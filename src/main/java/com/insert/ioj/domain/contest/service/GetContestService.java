package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problemContest.domain.repository.ProblemContestRepository;
import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.insert.ioj.domain.problemscore.domain.repository.ProblemScoreRepository;
import com.insert.ioj.domain.submission.facade.EntityFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetContestService {
    private final UserFacade userFacade;
    private final EntityFacade entityFacade;
    private final ProblemScoreRepository problemScoreRepository;
    private final ProblemContestRepository problemContestRepository;

    public ContestResponse execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = entityFacade.getContestById(contestId);

        contest.isNotStarted();
        contest.checkRole(user.getAuthority());

        List<ProblemScore> problems = problemScoreRepository.findAllByContestAndUser(contestId, user.getId());

        Map<Long, ProblemInfo> problemVerdictMap = problems.stream()
            .collect(Collectors.toMap(
                ProblemScore::getProblemId,
                ProblemInfo::of,
                (existing, replacement) -> existing
            ));

        List<ListContestProblemResponse> contestProblems = problemContestRepository.getProblems(contest).stream()
            .map(it -> {
                ProblemInfo problemInfo = problemVerdictMap.get(it.getId());
                return new ListContestProblemResponse(
                    it,
                    problemInfo != null ? problemInfo.verdict() : null,
                    problemInfo != null ? problemInfo.score() : 0
                );
            }).toList();


        return new ContestResponse(contest, contestProblems);
    }

    @Builder
    private record ProblemInfo(Verdict verdict, int score) {
        public static ProblemInfo of(ProblemScore problemScore) {
            return ProblemInfo.builder()
                .verdict(problemScore.getVerdict())
                .score(problemScore.getScore())
                .build();
        }
    }

//    public ContestResponse execute(Long contestId) {
//        User user = userFacade.getCurrentUser();
//        Contest contest = entityFacade.getContestById(contestId);
//
//        contest.isNotStarted();
//        contest.checkRole(user.getAuthority());
//
//        List<ProblemStatusDto> problemStatuses = problemFacade.getProblemStatuses(contest, user);
//
//        List<ListContestProblemResponse> contestProblem = new ArrayList<>();
//        for (ProblemStatusDto problemStatus : problemStatuses) {
//            contestProblem.add(new ListContestProblemResponse(problemStatus));
//        }
//
//        return new ContestResponse(contest, contestProblem);
//    }
}
