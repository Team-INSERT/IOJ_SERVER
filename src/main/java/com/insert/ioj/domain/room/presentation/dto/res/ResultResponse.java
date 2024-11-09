package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.entry.domain.Entry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ResultResponse {
    private String nickname;
    private Integer correctProblem;
    private Integer totalProblem;
    private LocalTime finishedTime;
    private Integer useItemCnt;
    private Integer protectCnt;

    public ResultResponse(Entry entry,
                          Integer correctProblemCnt,
                          Integer totalProblem,
                          Integer useItemCnt,
                          Integer protectCnt) {
        this.nickname = entry.getUser().getNickname();
        this.correctProblem = correctProblemCnt;
        this.totalProblem = totalProblem;
        this.finishedTime = entry.getFinishTime();
        this.useItemCnt = useItemCnt;
        this.protectCnt = protectCnt;
    }
}
