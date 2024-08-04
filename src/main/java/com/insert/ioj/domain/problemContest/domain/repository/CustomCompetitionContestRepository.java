package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;

public interface CustomCompetitionContestRepository {
    List<ListContestProblemResponse> getContestProblemsWithStatus(Contest contest, User user);
}
