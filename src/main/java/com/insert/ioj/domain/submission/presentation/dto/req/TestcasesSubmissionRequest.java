package com.insert.ioj.domain.submission.presentation.dto.req;

import com.insert.ioj.domain.execution.language.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TestcasesSubmissionRequest(
    @NotNull(message = "problemId가 비어있습니다.")
    Long problemId,

    @NotNull(message = "sourcecode가 비어있습니다.")
    String sourcecode,

    @NotNull(message = "language가 비어있습니다.")
    Language language,

    @Valid
    @NotNull(message = "testcaseResultDto가 비어있습니다.")
    List<TestcaseResultDto> testcaseResultDto
) {
    public record TestcaseResultDto(
        @NotNull(message = "input이 비어있습니다.")
        String input,

        @NotNull(message = "expectedOutput이 비어있습니다.")
        String expectedOutput
    ) {}
}
