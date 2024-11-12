package com.insert.ioj.domain.solve.room.repository;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.presentation.dto.res.GameResult;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;

public interface CustomSolveRoomRepository {
    Boolean existsByCorrectProblem(Room room, User user, Problem problem);
    List<GameResult> getGameResult(Room room);
}
