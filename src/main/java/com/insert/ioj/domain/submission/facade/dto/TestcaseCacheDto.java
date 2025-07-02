package com.insert.ioj.domain.submission.facade.dto;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestcaseCacheDto {
    private Long id;

    private String input;

    private String output;

    private boolean example;

    public TestcaseCacheDto(Testcase testcase) {
        this.input = testcase.getInput();
        this.output = testcase.getOutput();
        this.example = testcase.getExample();
    }

    public Testcase toTestcase() {
        return new Testcase(input, output, example);
    }
}
