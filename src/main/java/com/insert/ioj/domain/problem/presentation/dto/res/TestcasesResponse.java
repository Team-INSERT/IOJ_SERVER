package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestcasesResponse {
    private String input;
    private String output;
    private String expectOutput;
    private Verdict verdict;

    public TestcasesResponse(Testcase testcase, String output, Verdict verdict) {
        this.input = testcase.getInput();
        this.output = output;
        this.expectOutput = testcase.getOutput();
        this.verdict = verdict;
    }
}