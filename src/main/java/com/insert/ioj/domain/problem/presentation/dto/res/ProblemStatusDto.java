package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import lombok.Getter;

@Getter
public class ProblemStatusDto {
    private Problem problem;
    private String status;

    public ProblemStatusDto(Problem problem, String status) {
        this.problem = problem;
        this.status = status;
    }
}
