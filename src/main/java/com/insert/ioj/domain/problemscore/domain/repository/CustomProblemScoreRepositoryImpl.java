package com.insert.ioj.domain.problemscore.domain.repository;

import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.problemscore.domain.QProblemScore.problemScore;

@RequiredArgsConstructor
@Repository
public class CustomProblemScoreRepositoryImpl implements CustomProblemScoreRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<ProblemScore> findAllByContestAndUser(Long contestId, Long userId) {
        return queryFactory
            .selectFrom(problemScore)
            .where(
                problemScore.contest.id.eq(contestId)
                    .and(problemScore.user.id.eq(userId))
            )
            .fetch();
    }
}
