package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.user.domain.User;

public interface CustomContestSubmissionRepository {
    Boolean existsByCorrectProblem(Contest contest, User user, Problem problem);
}
