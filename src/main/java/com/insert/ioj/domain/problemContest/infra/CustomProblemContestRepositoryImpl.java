package com.insert.ioj.domain.problemContest.infra;

import com.insert.ioj.domain.problemContest.domain.repository.CustomCompetitionContestRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProblemContestRepositoryImpl implements CustomCompetitionContestRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
