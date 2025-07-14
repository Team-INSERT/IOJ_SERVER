package com.insert.ioj.domain.problem.problem.presentation.dto.res;

import com.insert.ioj.domain.subtask.domain.Subtask;

public record SubtaskResponse(
    int score,
    String description
) {
    public static SubtaskResponse from(Subtask subtask) {
        return new SubtaskResponse(
            subtask.getPerfectScore(), subtask.getDescription()
        );
    }
}
