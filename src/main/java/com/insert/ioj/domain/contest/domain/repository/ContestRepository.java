package com.insert.ioj.domain.contest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findAllByEndTimeAfter(LocalDateTime now);
}
