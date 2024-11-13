package com.insert.ioj.domain.room.domain.repository;

import com.insert.ioj.domain.room.domain.Room;

import java.util.List;

public interface CustomRoomRepository {
    List<Room> findAllDesc();
}
