package com.insert.ioj.infra.testcase;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.presentation.dto.res.TestcaseResult;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.status.StatusUtil;

public class TestcaseUtil {
    public static TestcaseResult testcaseResponse(ProcessOutput processOutput, Testcase testcase) {
        String output = processString(processOutput.getStdOut());
        boolean ans = testcase.getOutput().equals(output);
        Verdict verdict = StatusUtil.statusResponse(processOutput.getStatus(), ans);

        return new TestcaseResult(
            verdict,
            processOutput.getStdOut(),
            processOutput.getStdErr(),
            testcase,
            processOutput.getExecutionDuration()
        );
    }

    private static String processString(String input) {
        String[] lines = input.split("\n");
        StringBuilder sb = new StringBuilder();

        for (String line : lines) {
            sb.append(line.stripLeading()).append("\n");
        }

        if (!input.endsWith("\n")) {
            sb.append("\n");
        }

        return sb.toString();
    }
}
