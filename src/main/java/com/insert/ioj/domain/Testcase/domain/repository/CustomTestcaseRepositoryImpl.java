package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.subtask.domain.Subtask;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.Testcase.domain.QTestcase.testcase;

@RequiredArgsConstructor
@Repository
public class CustomTestcaseRepositoryImpl implements CustomTestcaseRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Testcase> findAllByProblemIdASC(Long id) {
        return queryFactory
            .selectFrom(testcase)
            .where(testcase.problem.id.eq(id))
            .orderBy(testcase.orderId.asc())
            .fetch();
    }

    @Override
    public List<Testcase> findAllBySubtasksASC(List<Subtask> subtasks) {
        return queryFactory
            .selectFrom(testcase)
            .where(testcase.subtask.in(subtasks))
            .orderBy(testcase.orderId.asc())
            .fetch();
    }
}
