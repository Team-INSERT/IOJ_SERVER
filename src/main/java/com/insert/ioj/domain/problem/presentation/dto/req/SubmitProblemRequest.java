package com.insert.ioj.domain.problem.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitProblemRequest {
    @NotNull(message = "id가 비어있습니다.")
    private Long id;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;
}
