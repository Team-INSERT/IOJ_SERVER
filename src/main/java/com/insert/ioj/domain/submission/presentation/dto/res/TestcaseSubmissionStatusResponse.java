package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;

public record TestcaseSubmissionStatusResponse(
    String input,
    String output,
    String expectedOutput,
    Verdict verdict
) {
}
