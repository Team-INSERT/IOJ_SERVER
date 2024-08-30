package com.insert.ioj.domain.execution.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExecutionRequest {
    private String sessionId;
    private String sourcecode;
    private String language;
}

