package com.insert.ioj.domain.execution.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InputRequest {
    @NotNull(message = "sessionId가 비어있습니다.")
    private String sessionId;

    @NotNull(message = "input이 비어있습니다.")
    private String input;
}
