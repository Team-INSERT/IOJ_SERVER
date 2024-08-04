package com.insert.ioj.domain.compiler.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompileResponse {
    private String id;
    private int status;
    private String message;
    private String result;

    @Builder
    public CompileResponse(String id, int status, String message, String result) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
