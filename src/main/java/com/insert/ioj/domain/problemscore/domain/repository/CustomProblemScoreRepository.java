package com.insert.ioj.domain.problemscore.domain.repository;

import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.RankElement;

import java.util.List;

public interface CustomProblemScoreRepository {
    List<ProblemScore> findAllByContestAndUser(Long contestId, Long userId);
    List<RankElement> getRankings(Long contestId);
}
