package com.insert.ioj.domain.execution.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExecutionRequest {
    @NotNull(message = "sessionId가 비어있습니다.")
    private String sessionId;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;

    @NotNull(message = "language가 비어있습니다.")
    private String language;
}

