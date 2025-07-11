package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.subtask.domain.Subtask;

import java.util.List;

public interface CustomTestcaseRepository {
    List<Testcase> findAllByProblemIdASC(Long id);
    List<Testcase> findAllBySubtasksASC(List<Subtask> subtasks);
}
