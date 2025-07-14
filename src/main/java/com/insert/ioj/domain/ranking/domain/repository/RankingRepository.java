package com.insert.ioj.domain.ranking.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.ranking.domain.Ranking;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long>, CustomRankingRepository {
    Optional<Ranking> findByContestAndUser(Contest contest, User user);
}
