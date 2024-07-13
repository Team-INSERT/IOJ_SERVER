package com.insert.ioj.domain.problemCompetition.domain.repository;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionProblemResponse;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemCompetitionRepository extends JpaRepository<ProblemCompetition, Long> {
    ProblemCompetition findByCompetitionAndProblem(Competition competition, Problem problem);

    @Query("SELECT new com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionProblemResponse(p.level, p.title, " +
        "CASE " +
        "WHEN sc.isPass = true THEN 'solved' " +
        "WHEN sc.isPass = false THEN 'failed' " +
        "ELSE 'unsolved' " +
        "END) " +
        "FROM ProblemCompetition pc " +
        "JOIN pc.problem p " +
        "LEFT JOIN SolveCompetition sc ON pc = sc.problemCompetition AND sc.user = :user " +
        "WHERE pc.competition = :competition")
    List<ListCompetitionProblemResponse> getCompetitionProblemsWithStatus(@Param("competition") Competition competition, @Param("user") User user);
}
