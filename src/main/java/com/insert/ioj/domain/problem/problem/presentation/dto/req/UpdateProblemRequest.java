package com.insert.ioj.domain.problem.problem.presentation.dto.req;

public record UpdateProblemRequest(
    Long id,
    String content,
    String inputContent,
    String outputContent
) {
}
