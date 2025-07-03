package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;

import java.util.List;

public interface CustomTestcaseRepository {
    List<Testcase> findAllByProblemIdASC(Long id);
}
