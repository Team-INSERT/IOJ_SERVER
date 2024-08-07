package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.CustomProblemRepository;
import com.insert.ioj.domain.solveContest.domain.SolveContest;
import com.insert.ioj.domain.solveContest.domain.repository.CustomSolveContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetContestService {
    private final UserFacade userFacade;
    private final ContestFacade contestFacade;
    private final CustomProblemRepository customProblemRepository;
    private final CustomSolveContestRepository customSolveContestRepository;

    public ContestResponse execute(Long contestId) {
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(contestId);
        contest.checkRole(user.getAuthority());

        List<Problem> problems = customProblemRepository.getContestProblems(contest);
        List<SolveContest> solveContests = customSolveContestRepository.getUserSolveContest(user, contest);

        Map<Long, SolveContest> latestSolveContests = new HashMap<>();
        for (SolveContest solveContest : solveContests) {
            latestSolveContests.putIfAbsent(solveContest.getProblem().getId(), solveContest);
        }

        List<ListContestProblemResponse> problemStatuses = getProblemsWithStatus(problems, latestSolveContests);

        return new ContestResponse(contest, problemStatuses);
    }

    private static List<ListContestProblemResponse> getProblemsWithStatus(List<Problem> problems,
                                                                          Map<Long, SolveContest> latestSolveContests) {
        List<ListContestProblemResponse> problemStatuses = new ArrayList<>();
        for (Problem problem : problems) {
            SolveContest solveContest = latestSolveContests.get(problem.getId());
            String status = "solved";
            if (solveContest == null) {
                status = "unsolved";
            } else if (!solveContest.isPass()) {
                status = "failed";
            }

            problemStatuses.add(
                new ListContestProblemResponse(
                    problem,
                    status
                )
            );
        }
        return problemStatuses;
    }
}
