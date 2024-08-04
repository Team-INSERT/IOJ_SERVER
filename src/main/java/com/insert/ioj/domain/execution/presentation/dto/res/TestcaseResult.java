package com.insert.ioj.domain.execution.presentation.dto.res;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.solve.domain.Solve;
import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;

@Getter
public class TestcaseResult {
    private Verdict verdict;
    private String statusResponse;
    private String output;
    private String error;
    private Testcase testcase;
    private int executionDuration;

    public TestcaseResult(Verdict verdict, String output, String error, Testcase testcase, int executionDuration) {
        this.verdict = verdict;
        this.statusResponse = verdict.getStatusResponse();
        this.output = output;
        this.error = error;
        this.testcase = testcase;
        this.executionDuration = executionDuration;
    }

    public Solve toEntity(User user,
                          Problem problem,
                          String sourcecode,
                          Verdict verdict,
                          Language language) {
        return new Solve(
            user,
            problem,
            sourcecode,
            verdict,
            language
        );
    }
}
