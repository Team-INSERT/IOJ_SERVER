package com.insert.ioj.domain.solve.room.repository;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.presentation.dto.res.GameResult;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.solve.room.QSolveRoom.solveRoom;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@RequiredArgsConstructor
public class CustomSolveRoomRepositoryImpl implements CustomSolveRoomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsByCorrectProblem(Room room, User user, Problem problem) {
        Integer fetchOne = queryFactory
            .selectOne()
            .from(solveRoom)
            .where(solveRoom.room.eq(room).and(solveRoom.user.eq(user)
                .and(solveRoom.problem.eq(problem)
                .and(solveRoom.verdict.eq(Verdict.ACCEPTED)))))
            .fetchOne();
        return fetchOne != null;
    }

    @Override
    public List<GameResult> getGameResult(Room room) {
        return queryFactory
            .select(constructor(GameResult.class,
                solveRoom.user,
                solveRoom.verdict.when(Verdict.ACCEPTED).then(1)
                    .otherwise(0).sum(),
                solveRoom.createdAt.max()))
            .from(solveRoom)
            .where(solveRoom.room.eq(room))
            .groupBy(solveRoom.user)
            .orderBy(
                solveRoom.verdict.when(Verdict.ACCEPTED).then(1)
                    .otherwise(0).sum().desc(),
                solveRoom.createdAt.max().asc())
            .fetch();
    }
}
