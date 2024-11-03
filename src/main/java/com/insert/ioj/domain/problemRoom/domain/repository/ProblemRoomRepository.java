package com.insert.ioj.domain.problemRoom.domain.repository;

import com.insert.ioj.domain.problemRoom.domain.ProblemRoom;
import com.insert.ioj.domain.room.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProblemRoomRepository extends CrudRepository<ProblemRoom, Long> {
    List<ProblemRoom> findByRoom(Room room);
}
