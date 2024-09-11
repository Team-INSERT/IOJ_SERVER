package com.insert.ioj.domain.entry.domain.repository;

import com.insert.ioj.domain.entry.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
