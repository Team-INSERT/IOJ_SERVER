package com.insert.ioj.domain.subtask.domain;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.global.entity.BaseTimeEntity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subtask extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int perfectScore;

    private int totalTestcases;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public Subtask(int perfectScore, int totalTestcases, String description, Problem problem) {
        this.perfectScore = perfectScore;
        this.totalTestcases = totalTestcases;
        this.description = description;
        this.problem = problem;
    }
}
