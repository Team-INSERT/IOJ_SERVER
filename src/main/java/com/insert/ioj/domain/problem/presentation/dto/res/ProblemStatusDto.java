package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProblemStatusDto {
    private Problem problem;
    private String status;
    private LocalDateTime solveTime;

    public ProblemStatusDto(Problem problem, String status, LocalDateTime solveTime) {
        this.problem = problem;
        this.status = status;
        this.solveTime = solveTime;
    }
}
