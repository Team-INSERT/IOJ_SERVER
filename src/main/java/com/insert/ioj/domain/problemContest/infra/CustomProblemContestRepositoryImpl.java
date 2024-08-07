package com.insert.ioj.domain.problemContest.infra;

import com.insert.ioj.domain.problemContest.domain.repository.CustomProblemContestRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProblemContestRepositoryImpl implements CustomProblemContestRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
