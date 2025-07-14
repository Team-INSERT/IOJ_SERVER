package com.insert.ioj.domain.problem.problem.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problemContest.domain.ProblemContest;

import java.util.List;

public interface CustomProblemRepository {
    List<ProblemContest> getContestProblems(Contest contest);
    List<Problem> getBetweenLevelProblems(int minLevel, int maxLevel);
}
