package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestcaseDto {
    private String input;
    private String output;

    public Testcase toEntity() {
        return new Testcase(input, output+"\n");
    }
}
