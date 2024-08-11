package com.insert.ioj.domain.solveContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListRankResponse;
import com.insert.ioj.domain.solveContest.domain.SolveContest;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;

public interface CustomSolveContestRepository {
    List<SolveContest> getUserSolveContest(User user, Contest contest);
    List<ListRankResponse> getRankingUser(Contest contest);
}
