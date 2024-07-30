package com.insert.ioj.domain.contest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestAdminResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findAllByEndTimeAfter(LocalDateTime now);
}
