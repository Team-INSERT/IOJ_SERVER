package com.insert.ioj.domain.problemContest.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemContest.domain.ProblemContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemContestRepository extends JpaRepository<ProblemContest, Long> {
    @Query("SELECT problem FROM ProblemContest WHERE contest = :contest")
    List<Problem> getProblems(@Param("contest") Contest contest);
}
