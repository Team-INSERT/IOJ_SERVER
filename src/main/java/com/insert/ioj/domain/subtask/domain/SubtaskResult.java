package com.insert.ioj.domain.subtask.domain;

import com.insert.ioj.domain.submission.domain.Submission;
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
public class SubtaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private int passedTestcases;

    private int totalTestcases;

    private Double maxExecutionTime;

    private int maxMemoryUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_id")
    private Subtask subtask;

    public SubtaskResult(
        int passedTestcases,
        Double maxExecutionTime, int maxMemoryUsed, Submission submission, Subtask subtask
    ) {
        this.score = subtask.getTotalTestcases() == passedTestcases ? subtask.getScore() : 0;
        this.passedTestcases = passedTestcases;
        this.totalTestcases = subtask.getTotalTestcases();
        this.maxExecutionTime = maxExecutionTime;
        this.maxMemoryUsed = maxMemoryUsed;
        this.submission = submission;
        this.subtask = subtask;
    }
}
