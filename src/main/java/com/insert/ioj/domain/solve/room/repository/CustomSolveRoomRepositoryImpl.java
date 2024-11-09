package com.insert.ioj.domain.solve.room.repository;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.insert.ioj.domain.solve.room.QSolveRoom.solveRoom;

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
    public Integer countByCorrectProblem(Room room, User user) {
        return queryFactory
            .selectFrom(solveRoom)
            .where(
                solveRoom.verdict.eq(Verdict.ACCEPTED),
                solveRoom.room.eq(room),
                solveRoom.user.eq(user)
            )
            .fetch().size();
    }
}
