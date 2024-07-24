package com.insert.ioj.domain.contest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
