package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.domain.ContestSubmission;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;

public interface CustomContestSubmissionRepository {
    Boolean existsByCorrectProblem(Contest contest, Long userId, Problem problem);
    List<ContestSubmission> getUserProblemSubmission(User user, Contest contest);
}
