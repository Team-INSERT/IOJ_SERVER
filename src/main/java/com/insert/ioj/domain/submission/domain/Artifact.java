package com.insert.ioj.domain.submission.domain;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Artifact extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String stdout;

    @Column(columnDefinition = "TEXT")
    private String stderr;

    @Column(columnDefinition = "TEXT")
    private String meta;

    private Double executionTime;
    private int memoryUsage;
    private int exitCode;

    @Enumerated(EnumType.STRING)
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    public Artifact(String stdout, String stderr, String meta, Submission submission) {
        this.stdout = stdout;
        this.stderr = stderr;
        this.meta = meta;
        this.submission = submission;
    }

    public Artifact update(
        Double executionTime,
        int memoryUsage,
        int exitCode,
        Verdict verdict
    ) {
        this.executionTime = executionTime;
        this.memoryUsage = memoryUsage;
        this.exitCode = exitCode;
        this.verdict = verdict;

        return this;
    }
}
