package com.insert.ioj.domain.problemscore.domain.repository;

import com.insert.ioj.domain.problemscore.domain.ProblemScore;

import java.util.List;

public interface CustomProblemScoreRepository {
    List<ProblemScore> findAllByContestAndUser(Long contestId, Long userId);
}
