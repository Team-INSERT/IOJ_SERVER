package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemIds {
    private Long id;
    private Long orderId;

    public ProblemIds(Long problemId, Long orderId) {
        this.id = problemId;
        this.orderId = orderId;
    }
}
