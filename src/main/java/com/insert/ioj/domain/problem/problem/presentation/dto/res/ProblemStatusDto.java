package com.insert.ioj.domain.problem.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problemContest.domain.ProblemContest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProblemStatusDto {
    private Problem problem;
    private String status;
    private LocalDateTime solveTime;

    public ProblemStatusDto(ProblemContest problem, String status, LocalDateTime solveTime) {
        this.problem = problem.getProblem();
        this.status = status;
        this.solveTime = solveTime;
    }
}
