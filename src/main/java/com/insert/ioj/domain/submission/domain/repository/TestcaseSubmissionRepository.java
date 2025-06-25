package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.TestcaseSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestcaseSubmissionRepository extends JpaRepository<TestcaseSubmission, UUID> {
    List<TestcaseSubmission> findAllBySubmission(Submission submission);
}
