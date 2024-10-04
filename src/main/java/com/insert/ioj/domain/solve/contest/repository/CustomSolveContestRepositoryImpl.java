package com.insert.ioj.domain.solve.contest.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListRankResponse;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.solve.contest.SolveContest;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.solve.contest.QSolveContest.solveContest;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@RequiredArgsConstructor
public class CustomSolveContestRepositoryImpl implements CustomSolveContestRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SolveContest> getUserSolveContest(User user, Contest contest) {
        return queryFactory
            .selectFrom(solveContest)
            .where(solveContest.user.eq(user)
                .and(solveContest.contest.eq(contest)))
            .orderBy(solveContest.problem.id.asc(), solveContest.createdAt.desc())
            .fetch();
    }

    @Override
    public List<ListRankResponse> getRankingUser(Contest contest) {
        return queryFactory
            .select(constructor(ListRankResponse.class,
                solveContest.user,
                solveContest.verdict.count(),
                solveContest.createdAt.max()))
            .from(solveContest)
            .where(solveContest.contest.eq(contest)
                .and(solveContest.verdict.eq(Verdict.ACCEPTED)))
            .groupBy(solveContest.user)
            .orderBy(solveContest.verdict.count().desc(),
                solveContest.createdAt.max().asc())
            .fetch();
    }

    @Override
    public Boolean existsByCorrectProblem(Contest contest, User user, Problem problem) {
        Integer fetchOne = queryFactory
            .selectOne()
            .from(solveContest)
            .where(solveContest.contest.eq(contest).and(solveContest.user.eq(user)
                .and(solveContest.problem.eq(problem)
                .and(solveContest.verdict.eq(Verdict.ACCEPTED)))))
            .fetchOne();
        return fetchOne != null;
    }
}
