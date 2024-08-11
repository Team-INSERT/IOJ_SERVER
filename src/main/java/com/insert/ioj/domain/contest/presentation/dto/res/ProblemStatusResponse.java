package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemStatusResponse {
    private String status;
    private Long penalty;

    public ProblemStatusResponse(String status, Long penalty) {
        this.status = status;
        this.penalty = penalty;
    }
}
