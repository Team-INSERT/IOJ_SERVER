package com.insert.ioj.domain.problemTestcase.domain.repository;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemTestcaseRepository extends JpaRepository<ProblemTestcase, Long> {
    Optional<List<ProblemTestcase>> findAllByProblem(Problem problem);
}
