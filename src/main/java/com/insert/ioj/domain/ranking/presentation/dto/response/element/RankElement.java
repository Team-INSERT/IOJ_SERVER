package com.insert.ioj.domain.ranking.presentation.dto.response.element;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class RankElement {
    private Long userId;
    private String userName;
    private int totalScore;
    private LocalDateTime achievedAt;

    public RankElement(Long userId, String userName, int totalScore, LocalDateTime achievedAt) {
        this.userId = userId;
        this.userName = userName;
        this.totalScore = totalScore;
        this.achievedAt = achievedAt;
    }
}
