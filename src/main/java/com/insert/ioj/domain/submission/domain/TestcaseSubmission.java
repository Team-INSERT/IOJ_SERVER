package com.insert.ioj.domain.submission.domain;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
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
public class TestcaseSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderId;

    @Column(columnDefinition = "TEXT")
    String input;

    @Column(columnDefinition = "TEXT")
    String output;

    @Column(columnDefinition = "TEXT")
    String expectedOutput;

    @Enumerated(EnumType.STRING)
    Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    public TestcaseSubmission(int orderId, String input, String expectedOutput, Submission submission) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.submission = submission;
    }

    public void updateVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public Testcase toTestcase() {
        return new Testcase(
            orderId, input, expectedOutput, false, submission.getProblem()
        );
    }
}
