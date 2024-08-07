package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListContestProblemResponse {
    private Long id;
    private int level;
    private String title;
    private String status;

    public ListContestProblemResponse(Problem problem, String status) {
        this.id = problem.getId();
        this.level = problem.getLevel();
        this.title = problem.getTitle();
        this.status = status;
    }
}
