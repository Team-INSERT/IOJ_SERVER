package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.execution.language.Language;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExecutionProblemRequest {
    @NotNull(message = "id가 비어있습니다.")
    private Long id;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;

    @NotNull(message = "language가 비어있습니다.")
    private Language language;
}
