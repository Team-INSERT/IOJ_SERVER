package com.insert.ioj.domain.solveCompetition.domain;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SolveCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_competition_id")
    private ProblemCompetition problemCompetition;

    public SolveCompetition(User user, ProblemCompetition problemCompetition) {
        this.user = user;
        this.problemCompetition = problemCompetition;
    }
}
