package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problemscore.domain.repository.ProblemScoreRepository;
import com.insert.ioj.domain.submission.facade.EntityFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetContestService {
    private final UserFacade userFacade;
    private final EntityFacade entityFacade;
    private final ProblemScoreRepository problemScoreRepository;

    public ContestResponse execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = entityFacade.getContestById(contestId);

        contest.isNotStarted();
        contest.checkRole(user.getAuthority());

        List<ListContestProblemResponse> problems = problemScoreRepository.findAllByContestAndUser(contestId, user.getId()).stream()
            .map(it -> {
                Problem problem = entityFacade.getProblemById(it.getProblemId());
                return new ListContestProblemResponse(it.getVerdict(), problem);
            }).toList();

        return new ContestResponse(contest, problems);
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
