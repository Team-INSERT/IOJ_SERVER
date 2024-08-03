package com.insert.ioj.domain.problemContest.infra;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problemContest.domain.repository.CustomCompetitionContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.problem.domain.QProblem.problem;
import static com.insert.ioj.domain.problemContest.domain.QProblemContest.problemContest;
import static com.insert.ioj.domain.solveContest.domain.QSolveContest.solveContest;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@RequiredArgsConstructor
public class CustomProblemContestRepositoryImpl implements CustomCompetitionContestRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ListContestProblemResponse> getContestProblemsWithStatus(Contest contest, User user) {
        return jpaQueryFactory
            .select(constructor(ListContestProblemResponse.class,
                problem.id, problem.level, problem.title,
                new CaseBuilder()
                    .when(solveContest.isPass.isTrue()).then("solved")
                    .when(solveContest.isPass.isFalse()).then("failed")
                    .otherwise("unsolved")))
            .from(problemContest)
            .join(problemContest.problem, problem)
            .leftJoin(solveContest).on(problemContest.eq(solveContest.problemContest)
                .and(solveContest.user.eq(user)))
            .where(problemContest.contest.eq(contest))
            .fetch();
    }
}
