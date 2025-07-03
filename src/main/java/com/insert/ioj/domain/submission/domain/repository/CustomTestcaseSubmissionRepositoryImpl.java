package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.TestcaseSubmission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.submission.domain.QTestcaseSubmission.testcaseSubmission;

@Repository
@RequiredArgsConstructor
public class CustomTestcaseSubmissionRepositoryImpl implements CustomTestcaseSubmissionRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TestcaseSubmission> findAllBySubmission(Submission submission) {
        return queryFactory
            .selectFrom(testcaseSubmission)
            .where(testcaseSubmission.submission.eq(submission))
            .orderBy(testcaseSubmission.orderId.asc())
            .fetch();
    }
}
