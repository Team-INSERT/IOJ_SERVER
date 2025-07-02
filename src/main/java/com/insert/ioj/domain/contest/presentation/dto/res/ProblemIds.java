package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemIds {
    private Long id;

    public ProblemIds(Long problemId) {
        this.id = problemId;
    }
}
