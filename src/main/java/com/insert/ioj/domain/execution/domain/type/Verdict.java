package com.insert.ioj.domain.execution.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Verdict {
    ACCEPTED("Accepted"),
    WRONG_ANSWER("Wrong Answer"),
    COMPILATION_ERROR("Compilation Error"),
    OUT_OF_MEMORY("Out Of Memory"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded"),
    RUNTIME_ERROR("Runtime Error");

    private String statusResponse;
}
