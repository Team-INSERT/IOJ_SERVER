package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ProblemStatusDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListContestProblemResponse {
    private Long id;
    private int level;
    private String title;
    private String status;

    public ListContestProblemResponse(ProblemStatusDto problemStatus) {
        Problem problem = problemStatus.getProblem();
        this.id = problem.getId();
        this.level = problem.getLevel();
        this.title = problem.getTitle();
        this.status = problemStatus.getStatus();
    }
}
