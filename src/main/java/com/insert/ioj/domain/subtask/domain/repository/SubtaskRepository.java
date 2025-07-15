package com.insert.ioj.domain.subtask.domain.repository;

import com.insert.ioj.domain.subtask.domain.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findAllByProblem_Id(Long problemId);
}
