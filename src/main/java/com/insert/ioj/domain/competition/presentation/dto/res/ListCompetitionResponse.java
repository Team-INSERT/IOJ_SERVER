package com.insert.ioj.domain.competition.presentation.dto.res;

import com.insert.ioj.domain.competition.domain.Competition;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ListCompetitionResponse {
    private Long id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ListCompetitionResponse(Competition competition) {
        this.id = competition.getId();
        this.title = competition.getTitle();
        this.startTime = competition.getStartTime();
        this.endTime = competition.getEndTime();
    }
}
