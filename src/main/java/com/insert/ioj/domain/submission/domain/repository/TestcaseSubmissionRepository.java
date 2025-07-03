package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.TestcaseSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestcaseSubmissionRepository extends JpaRepository<TestcaseSubmission, UUID>, CustomTestcaseSubmissionRepository {
}
