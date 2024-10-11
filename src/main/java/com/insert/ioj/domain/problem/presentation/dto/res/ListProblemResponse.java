package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListProblemResponse {
    private Long id;
    private String title;
    private int level;
    private String source;

    public ListProblemResponse(Problem problem) {
        this.id = problem.getId();
        this.title = problem.getTitle();
        this.level = problem.getLevel();
        this.source = problem.getSource();
    }
}
