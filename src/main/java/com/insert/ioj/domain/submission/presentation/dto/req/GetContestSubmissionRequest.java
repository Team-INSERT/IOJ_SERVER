package com.insert.ioj.domain.submission.presentation.dto.req;

import jakarta.validation.constraints.NotNull;

public record GetContestSubmissionRequest(
    @NotNull(message = "contestId가 비어있습니다.")
    Long contestId,

    @NotNull(message = "problemId가 비어있습니다.")
    Long problemId
) {}
