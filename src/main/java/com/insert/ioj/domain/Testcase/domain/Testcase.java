package com.insert.ioj.domain.Testcase.domain;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.subtask.domain.Subtask;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Testcase extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int orderId;

    @Column(columnDefinition = "TEXT")
    private String input;

    @Column(columnDefinition = "TEXT")
    private String output;

    private Boolean example;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_id")
    private Subtask subtask;

    public Testcase(int orderId, String input, String output, Boolean example, Problem problem, Subtask subtask) {
        this.orderId = orderId;
        this.input = input;
        this.output = output;
        this.example = example;
        this.problem = problem;
    }

    public Testcase(int orderId, String input, String output, Boolean example) {
        this.orderId = orderId;
        this.input = input;
        this.output = output;
        this.example = example;
    }
}
