package com.insert.ioj.domain.problemscore.domain.repository;

import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.RankElement;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.insert.ioj.domain.problemscore.domain.QProblemScore.problemScore;

@RequiredArgsConstructor
@Repository
public class CustomProblemScoreRepositoryImpl implements CustomProblemScoreRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

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

    @Override
    public List<RankElement> getRankings(Long contestId) {
        String sql = """
            SELECT 
                RANK() OVER (ORDER BY total_score DESC, max_achieved_at ASC) as ranking,
                user_id,
                user_name,
                total_score,
                max_achieved_at,
                problems_attempted
            FROM (
                SELECT 
                    ps.user_id,
                    u.nickname as user_name,
                    SUM(ps.score) as total_score,
                    MAX(ps.achieved_at) as max_achieved_at,
                    COUNT(DISTINCT ps.problem_id) as problems_attempted
                FROM problem_score ps
                JOIN tbl_user u ON ps.user_id = u.id
                WHERE ps.contest_id = :contestId
                GROUP BY ps.user_id, u.nickname
            ) user_stats
            ORDER BY ranking
            """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("contestId", contestId);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream()
            .map(row -> new RankElement(
                ((Long) row[1]),
                (String) row[2],
                ((Number) row[3]).intValue(),
                (LocalDateTime) row[4]
            ))
            .collect(Collectors.toList());
    }
}
