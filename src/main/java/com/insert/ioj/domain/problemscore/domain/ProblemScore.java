package com.insert.ioj.domain.problemscore.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
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

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemScore extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @Enumerated(EnumType.STRING)
    private Verdict verdict;

    private LocalDateTime achievedAt;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private Long problemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProblemScore(int score, Verdict verdict, LocalDateTime achievedAt, Long problemId, Contest contest, User user) {
        this.score = score;
        this.verdict = verdict;
        this.achievedAt = achievedAt;
        this.problemId = problemId;
        this.contest = contest;
        this.user = user;
    }

    public void update(int score, Verdict verdict, LocalDateTime achievedAt) {
        this.score = score;
        this.verdict = verdict;
        this.achievedAt = achievedAt;
    }
}
