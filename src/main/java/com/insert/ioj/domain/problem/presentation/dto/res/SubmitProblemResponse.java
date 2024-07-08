package com.insert.ioj.domain.problem.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitProblemResponse {
    private Long id;
    private String compileStatus;
    private boolean isPass;

    public SubmitProblemResponse(Long id, String compileStatus, boolean isPass) {
        this.id = id;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
    }
}
