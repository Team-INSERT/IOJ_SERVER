package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.submission.domain.TestcaseSubmission;

import java.util.List;

public interface CustomTestcaseSubmissionRepository {
    List<TestcaseSubmission> findAllBySubmission(Submission submission);
}
