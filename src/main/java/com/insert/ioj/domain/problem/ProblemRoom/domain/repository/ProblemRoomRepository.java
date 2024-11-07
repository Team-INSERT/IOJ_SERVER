package com.insert.ioj.domain.problem.ProblemRoom.domain.repository;

import com.insert.ioj.domain.problem.ProblemRoom.domain.ProblemRoom;
import com.insert.ioj.domain.room.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProblemRoomRepository extends CrudRepository<ProblemRoom, Long> {
    List<ProblemRoom> findByRoom(Room room);
}
