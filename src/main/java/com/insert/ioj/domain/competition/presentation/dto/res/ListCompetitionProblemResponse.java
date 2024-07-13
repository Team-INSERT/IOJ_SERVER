package com.insert.ioj.domain.competition.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListCompetitionProblemResponse {
    private int level;
    private String title;
    private String status;

    public ListCompetitionProblemResponse(int level, String title, String status) {
        this.level = level;
        this.title = title;
        this.status = status;
    }
}
