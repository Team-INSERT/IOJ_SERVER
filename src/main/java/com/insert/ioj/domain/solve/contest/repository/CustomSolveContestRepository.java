package com.insert.ioj.domain.solve.contest.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListRankResponse;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.solve.contest.SolveContest;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;

public interface CustomSolveContestRepository {
    List<SolveContest> getUserSolveContest(User user, Contest contest);
    List<ListRankResponse> getRankingUser(Contest contest);
    Boolean existsByCorrectProblem(Contest contest, User user, Problem problem);
}
