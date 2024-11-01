package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.domain.Problem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.problem.domain.QProblem.problem;
import static com.insert.ioj.domain.problemContest.domain.QProblemContest.problemContest;

@Repository
@RequiredArgsConstructor
public class CustomProblemContestRepositoryImpl implements CustomProblemContestRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Problem> getProblems(Contest contest) {
        return queryFactory
            .select(problem)
            .from(problemContest)
            .where(problemContest.contest.eq(contest))
            .fetch();
    }
}
