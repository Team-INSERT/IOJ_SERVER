package com.insert.ioj.domain.problemContest.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemContest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    public ProblemContest(Problem problem, Contest contest) {
        this.problem = problem;
        this.contest = contest;
    }
}
