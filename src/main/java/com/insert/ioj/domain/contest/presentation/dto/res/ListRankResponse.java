package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ListRankResponse {
    private User user;
    private Long solve;
    private LocalDateTime penalty;

    public ListRankResponse(User user, Long solve, LocalDateTime penalty) {
        this.user = user;
        this.solve = solve;
        this.penalty = penalty;
    }
}
