package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.problemContest.domain.ProblemContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemContestRepository extends JpaRepository<ProblemContest, Long> {
}
