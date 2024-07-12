package com.insert.ioj.domain.problemCompetition.domain.repository;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemCompetitionRepository extends JpaRepository<ProblemCompetition, Long> {
    ProblemCompetition findByCompetitionAndProblem(Competition competition, Problem problem);
}
