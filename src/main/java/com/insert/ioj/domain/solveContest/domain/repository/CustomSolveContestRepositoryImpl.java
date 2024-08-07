package com.insert.ioj.domain.solveContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.solveContest.domain.SolveContest;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.solveContest.domain.QSolveContest.solveContest;

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
            .orderBy(solveContest.problem.id.asc(), solveContest.createDate.desc())
            .fetch();
    }
}
