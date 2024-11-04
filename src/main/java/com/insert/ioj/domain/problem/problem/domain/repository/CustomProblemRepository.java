package com.insert.ioj.domain.problem.problem.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;

import java.util.List;

public interface CustomProblemRepository {
    List<Problem> getContestProblems(Contest contest);
    List<Problem> getBetweenLevelProblems(int minLevel, int maxLevel);
}
