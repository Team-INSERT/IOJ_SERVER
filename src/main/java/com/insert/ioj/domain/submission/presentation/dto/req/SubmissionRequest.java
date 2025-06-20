package com.insert.ioj.domain.submission.presentation.dto.req;

import com.insert.ioj.domain.execution.language.Language;
import jakarta.validation.constraints.NotNull;

public record SubmissionRequest(
    @NotNull(message = "problemId가 비어있습니다.")
    Long problemId,

    @NotNull(message = "sourcecode가 비어있습니다.")
    String sourcecode,

    @NotNull(message = "language가 비어있습니다.")
    Language language
) {}
