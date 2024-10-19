package com.insert.ioj.domain.problemRoom.domain.repository;

import com.insert.ioj.domain.problemRoom.domain.ProblemRoom;
import com.insert.ioj.domain.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRoomRepository extends JpaRepository<ProblemRoom, Long> {
    List<ProblemRoom> findByRoom(Room room);
}
