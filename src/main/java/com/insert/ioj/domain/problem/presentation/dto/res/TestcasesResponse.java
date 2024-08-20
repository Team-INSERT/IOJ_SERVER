package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestcasesResponse {
    private int index;
    private String input;
    private String output;
    private String expectOutput;
    private Verdict verdict;

    public TestcasesResponse(int index, Testcase testcase, String output, Verdict verdict) {
        this.index = index;
        this.input = testcase.getInput();
        this.output = output;
        this.expectOutput = testcase.getOutput();
        this.verdict = verdict;
    }
}
