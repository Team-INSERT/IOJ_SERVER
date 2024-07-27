package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemContest.domain.ProblemContest;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemContestRepository extends JpaRepository<ProblemContest, Long> {
    ProblemContest findByContestAndProblem(Contest contest, Problem problem);

    @Query("SELECT new com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse(p.id, p.level, p.title, " +
        "CASE " +
        "WHEN sc.isPass = true THEN 'solved' " +
        "WHEN sc.isPass = false THEN 'failed' " +
        "ELSE 'unsolved' " +
        "END) " +
        "FROM ProblemContest pc " +
        "JOIN pc.problem p " +
        "LEFT JOIN SolveContest sc ON pc = sc.problemContest AND sc.user = :user " +
        "WHERE pc.contest = :contest")
    List<ListContestProblemResponse> getContestProblemsWithStatus(@Param("contest") Contest contest, @Param("user") User user);
}
