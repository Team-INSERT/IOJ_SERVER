package com.insert.ioj.domain.Testcase.domain.repository;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
}
