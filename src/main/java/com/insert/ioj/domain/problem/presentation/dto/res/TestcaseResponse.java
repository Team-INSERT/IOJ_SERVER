package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestcaseResponse {
    private String input;
    private String output;

    public TestcaseResponse(Testcase testcase) {
        this.input = testcase.getInput();
        this.output = testcase.getOutput();
        if (output.endsWith("\n")) {
            output = output.substring(0, output.length() - 1);
        }
    }
}
