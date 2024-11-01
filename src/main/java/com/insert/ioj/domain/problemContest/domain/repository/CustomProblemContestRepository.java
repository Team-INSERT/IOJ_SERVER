package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.domain.Problem;

import java.util.List;

public interface CustomProblemContestRepository {
    List<Problem> getProblems(Contest contest);
}
