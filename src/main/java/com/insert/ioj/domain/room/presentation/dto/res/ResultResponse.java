package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResultResponse {
    private String nickname;
    private Color color;
    private Integer correctProblem;
    private Integer totalProblem;
    private LocalDateTime finishedTime;
    private Integer useItemCnt;
    private Integer protectCnt;

    public ResultResponse(User user,
                          Integer correctProblemCnt,
                          Integer totalProblemCnt,
                          LocalDateTime finishTime,
                          Integer useItemCnt,
                          Integer protectCnt) {
        this.nickname = user.getNickname();
        this.color = user.getColor();
        this.correctProblem = correctProblemCnt;
        this.totalProblem = totalProblemCnt;
        this.finishedTime = finishTime;
        this.useItemCnt = useItemCnt;
        this.protectCnt = protectCnt;
    }
}
