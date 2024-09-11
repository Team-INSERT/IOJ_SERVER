package com.insert.ioj.domain.entry.domain.repository;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findAllByRoom(Room room);
}
