package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.domain.ContestSubmission;
import com.insert.ioj.domain.submission.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContestSubmissionRepository extends JpaRepository<ContestSubmission, UUID>, CustomContestSubmissionRepository {
    Submission findByProblemAndContest(Problem problem, Contest contest);
}
