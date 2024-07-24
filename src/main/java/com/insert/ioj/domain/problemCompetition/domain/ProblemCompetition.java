package com.insert.ioj.domain.problemCompetition.domain;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    public ProblemCompetition(Problem problem, Competition competition) {
        this.problem = problem;
        this.competition = competition;
    }
}
