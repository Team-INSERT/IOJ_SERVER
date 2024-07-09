package com.insert.ioj.domain.competition.domain.repository;

import com.insert.ioj.domain.competition.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
