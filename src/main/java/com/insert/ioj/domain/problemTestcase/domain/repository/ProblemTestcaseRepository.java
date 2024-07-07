package com.insert.ioj.domain.problemTestcase.domain.repository;

import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTestcaseRepository extends JpaRepository<ProblemTestcase, Long> {
}
