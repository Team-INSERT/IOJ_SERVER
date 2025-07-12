package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.subtask.domain.SubtaskResult;

import java.util.List;

public record SubtaskInfo(
    int score,
    int perfectScore,
    int passedTestcases,
    int totalTestcases,
    Double maxExecutionTime,
    int maxMemoryUsed
) {
    public static List<SubtaskInfo> toEntities(List<SubtaskResult> subtaskResults) {
        return subtaskResults.stream()
            .map(SubtaskInfo::toEntity)
            .toList();
    }

    private static SubtaskInfo toEntity(SubtaskResult subtaskResult) {
        return new SubtaskInfo(
            subtaskResult.getScore(),
            subtaskResult.getSubtask().getPerfectScore(),
            subtaskResult.getPassedTestcases(),
            subtaskResult.getTotalTestcases(),
            subtaskResult.getMaxExecutionTime(),
            subtaskResult.getMaxMemoryUsed()
        );
    }
}
