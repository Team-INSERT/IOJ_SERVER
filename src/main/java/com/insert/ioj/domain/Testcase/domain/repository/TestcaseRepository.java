package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.subtask.domain.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestcaseRepository extends JpaRepository<Testcase, Long>, CustomTestcaseRepository {
    Optional<List<Testcase>> findAllByProblem(Problem problem);
    List<Testcase> findAllBySubtask(Subtask subtask);
    List<Testcase> findAllBySubtask_Id(Long subtaskId);
    List<Testcase> findAllByProblem_Id(Long problemId);
    Optional<List<Testcase>> findAllByProblemAndExampleIsTrue(Problem problem);
}
