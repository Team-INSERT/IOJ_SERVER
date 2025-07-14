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

        Map<Long, Verdict> problemVerdictMap = problems.stream()
            .collect(Collectors.toMap(
                ProblemScore::getProblemId,
                ProblemScore::getVerdict,
                (existing, replacement) -> existing
            ));

        List<ListContestProblemResponse> contestProblems = problemContestRepository.findAllByContest_Id(contestId).stream()
            .map(it -> {
                Verdict verdict = problemVerdictMap.get(it.getProblem().getId());
                return new ListContestProblemResponse(it.getProblem(), verdict);
            })
            .toList();


        return new ContestResponse(contest, contestProblems);
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
