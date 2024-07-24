package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.contest.domain.Contest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ListContestResponse {
    private Long id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ListContestResponse(Contest contest) {
        this.id = contest.getId();
        this.title = contest.getTitle();
        this.startTime = contest.getStartTime();
        this.endTime = contest.getEndTime();
    }
}
