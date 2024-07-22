package com.insert.ioj.domain.compiler.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompileCodeRequest {
    private String sourcecode;

    private String input;

    private String output;

    private int timeLimit;

    private int memoryLimit;
}
