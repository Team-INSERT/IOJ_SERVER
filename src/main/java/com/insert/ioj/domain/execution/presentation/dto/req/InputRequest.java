package com.insert.ioj.domain.execution.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InputRequest {
    private String sessionId;
    private String input;
}
