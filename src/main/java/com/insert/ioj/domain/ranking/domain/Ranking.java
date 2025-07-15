package com.insert.ioj.domain.ranking.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
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
public class Ranking extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private LocalDateTime achievedAt;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ranking(int score, LocalDateTime achievedAt, Contest contest, User user) {
        this.score = score;
        this.achievedAt = achievedAt;
        this.contest = contest;
        this.user = user;
    }

    public void update(int score, LocalDateTime achievedAt) {
        this.score += score;
        this.achievedAt = achievedAt;
    }
}
