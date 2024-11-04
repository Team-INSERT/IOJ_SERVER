package com.insert.ioj.domain.problem.problem.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.problem.problem.domain.QProblem.problem;
import static com.insert.ioj.domain.problem.problemContest.domain.QProblemContest.problemContest;

@Repository
@RequiredArgsConstructor
public class CustomProblemRepositoryImpl implements CustomProblemRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Problem> getContestProblems(Contest contest) {
        return queryFactory
            .selectFrom(problem)
            .join(problemContest).on(problem.eq(problemContest.problem))
            .where(problemContest.contest.eq(contest))
            .fetch();
    }

    @Override
    public List<Problem> getBetweenLevelProblems(int minLevel, int maxLevel) {
        return queryFactory
            .selectFrom(problem)
            .where(problem.level.between(minLevel, maxLevel))
            .fetch();
    }
}
