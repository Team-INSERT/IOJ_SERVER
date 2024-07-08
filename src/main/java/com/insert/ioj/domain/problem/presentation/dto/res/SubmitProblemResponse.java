package com.insert.ioj.domain.problem.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitProblemResponse {
    private Long id;
    private boolean isPass;

    public SubmitProblemResponse(Long id, boolean isPass) {
        this.id = id;
        this.isPass = isPass;
    }
}
