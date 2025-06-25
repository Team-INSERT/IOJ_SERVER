package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.ContestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContestSubmissionRepository extends JpaRepository<ContestSubmission, UUID>, CustomContestSubmissionRepository {
}
