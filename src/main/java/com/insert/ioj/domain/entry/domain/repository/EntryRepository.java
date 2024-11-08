package com.insert.ioj.domain.entry.domain.repository;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findAllByRoom(Room room);
    Optional<Entry> findByRoomAndUser(Room room, User user);
    Boolean existsByUserAndRoom(User user, Room room);

    @Query("SELECT COUNT(*) FROM Entry WHERE room = :room and ready = true")
    Long countIsReady(@Param("room") Room room);
}
