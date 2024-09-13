package com.insert.ioj.domain.room.domain.repository;

import com.insert.ioj.domain.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
