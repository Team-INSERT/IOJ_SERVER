package com.insert.ioj.domain.solve.domain;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Solve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String sourcecode;

    private String compileStatus;

    private boolean isPass;

    public Solve(User user, Problem problem, String sourcecode, String compileStatus, boolean isPass) {
        this.user = user;
        this.problem = problem;
        this.sourcecode = sourcecode;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
    }
}