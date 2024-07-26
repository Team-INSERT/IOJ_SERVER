package com.insert.ioj.domain.Testcase.domain;

import com.insert.ioj.domain.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Testcase {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String input;

    private String output;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public Testcase(String input, String output, Problem problem) {
        this.input = input;
        this.output = output;
        this.problem = problem;
    }
}
