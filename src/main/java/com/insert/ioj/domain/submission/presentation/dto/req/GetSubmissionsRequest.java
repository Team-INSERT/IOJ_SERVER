package com.insert.ioj.domain.submission.presentation.dto.req;

public record GetSubmissionsRequest(
    Long contestId,
    Long ProblemId
) {
}
