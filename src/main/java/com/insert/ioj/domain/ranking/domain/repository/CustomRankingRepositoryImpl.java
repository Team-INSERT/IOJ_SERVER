package com.insert.ioj.domain.ranking.domain.repository;

import com.insert.ioj.domain.ranking.domain.Ranking;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.ranking.domain.QRanking.ranking;

@RequiredArgsConstructor
@Repository
public class CustomRankingRepositoryImpl implements CustomRankingRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Ranking> getRankings(Long contestId) {
        return queryFactory
            .selectFrom(ranking)
            .where(ranking.contest.id.eq(contestId))
            .orderBy(ranking.score.desc(), ranking.achievedAt.asc())
            .fetch();
    }
}
