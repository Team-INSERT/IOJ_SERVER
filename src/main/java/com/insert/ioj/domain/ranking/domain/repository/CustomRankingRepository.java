package com.insert.ioj.domain.ranking.domain.repository;

import com.insert.ioj.domain.ranking.domain.Ranking;

import java.util.List;

public interface CustomRankingRepository {
    List<Ranking> getRankings(Long contestId);
}
