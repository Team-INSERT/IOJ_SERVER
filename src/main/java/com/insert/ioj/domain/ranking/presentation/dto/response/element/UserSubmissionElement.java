package com.insert.ioj.domain.ranking.presentation.dto.response.element;

import java.util.List;

public record UserSubmissionElement(
    List<SubmissionDetailElement> problems,
    Long userId
) {
}
