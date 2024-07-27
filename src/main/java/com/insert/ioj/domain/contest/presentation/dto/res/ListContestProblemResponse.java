package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListContestProblemResponse {
    private Long id;
    private int level;
    private String title;
    private String status;

    public ListContestProblemResponse(Long id, int level, String title, String status) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.status = status;
    }
}
