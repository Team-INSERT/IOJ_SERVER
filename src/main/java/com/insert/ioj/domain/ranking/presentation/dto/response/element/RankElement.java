package com.insert.ioj.domain.ranking.presentation.dto.response.element;

import com.insert.ioj.domain.ranking.domain.Ranking;
import com.insert.ioj.domain.user.domain.User;

import java.time.LocalDateTime;

public record RankElement(
    Long userId,
    String userName,
    int totalScore,
    LocalDateTime achievedAt
) {
    public static RankElement toEntity(Ranking ranking) {
        User user = ranking.getUser();
        return new RankElement(user.getId(), user.getNickname(), ranking.getScore(), ranking.getAchievedAt());
    }
}
