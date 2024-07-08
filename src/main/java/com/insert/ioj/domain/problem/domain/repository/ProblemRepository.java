package com.insert.ioj.domain.problem.domain.repository;

import com.insert.ioj.domain.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
