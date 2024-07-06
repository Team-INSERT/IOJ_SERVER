package com.insert.ioj.domain.compiler.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompileResponse {
    private String id;
    private String status;
    private String result;

    @Builder
    public CompileResponse(String id, String status, String result) {
        this.id = id;
        this.status = status;
        this.result = result;
    }
}
