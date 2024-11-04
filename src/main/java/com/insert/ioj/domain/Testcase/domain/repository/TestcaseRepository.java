package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    Optional<List<Testcase>> findAllByProblem(Problem problem);
    Optional<List<Testcase>> findAllByProblemAndExampleIsTrue(Problem problem);
}
