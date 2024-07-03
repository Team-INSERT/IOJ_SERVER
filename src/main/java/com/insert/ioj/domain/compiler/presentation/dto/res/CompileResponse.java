package com.insert.ioj.domain.compiler.presentation.dto.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompileResponse {
    private String id;
    private String result;

    public CompileResponse(String id, String result) {
        this.id = id;
        this.result = result;
    }
}
