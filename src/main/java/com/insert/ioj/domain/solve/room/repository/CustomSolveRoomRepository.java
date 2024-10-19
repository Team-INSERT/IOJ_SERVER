package com.insert.ioj.domain.solve.room.repository;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;

public interface CustomSolveRoomRepository {
    Boolean existsByCorrectProblem(Room room, User user, Problem problem);
}
