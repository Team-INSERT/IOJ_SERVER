package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GameResult {
    private User user;
    private Integer correctProblemCnt;
    private LocalDateTime finishTime;

    public GameResult(User user, Integer correctProblemCnt, LocalDateTime finishTime) {
        this.user = user;
        this.correctProblemCnt = correctProblemCnt;
        this.finishTime = finishTime;
    }
}
