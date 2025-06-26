package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.domain.ContestSubmission;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.submission.domain.QContestSubmission.contestSubmission;

@RequiredArgsConstructor
@Repository
public class CustomContestSubmissionRepositoryImpl implements CustomContestSubmissionRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsByCorrectProblem(Contest contest, User user, Problem problem) {
        Integer fetchOne = queryFactory
            .selectOne()
            .from(contestSubmission)
            .where(contestSubmission.contest.eq(contest).and(contestSubmission.user.eq(user)
                .and(contestSubmission.problem.eq(problem)
                    .and(contestSubmission.verdict.eq(Verdict.ACCEPTED)))))
            .fetchOne();
        return fetchOne != null;
    }

    @Override
    public List<ContestSubmission> getUserProblemSubmission(User user, Contest contest) {
        return queryFactory
            .selectFrom(contestSubmission)
            .where(contestSubmission.user.eq(user)
                .and(contestSubmission.contest.eq(contest)))
            .orderBy(contestSubmission.problem.id.asc(), contestSubmission.createdAt.desc())
            .fetch();
    }
}
