package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListContestProblemResponse {
    private int level;
    private String title;
    private String status;

    public ListContestProblemResponse(int level, String title, String status) {
        this.level = level;
        this.title = title;
        this.status = status;
    }
}
