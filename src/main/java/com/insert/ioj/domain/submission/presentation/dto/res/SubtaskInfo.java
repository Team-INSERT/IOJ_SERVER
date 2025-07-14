package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.subtask.domain.SubtaskResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class SubtaskInfo {
    private int score;
    private int perfectScore;
    private int passedTestcases;
    private int totalTestcases;
    private Double maxExecutionTime;
    private int maxMemoryUsed;
    private Verdict verdict;
    private String detail;

    public SubtaskInfo(int score, int perfectScore, int passedTestcases, int totalTestcases,
                       Double maxExecutionTime, int maxMemoryUsed, Verdict verdict, String detail) {
        this.score = score;
        this.perfectScore = perfectScore;
        this.passedTestcases = passedTestcases;
        this.totalTestcases = totalTestcases;
        this.maxExecutionTime = maxExecutionTime;
        this.maxMemoryUsed = maxMemoryUsed;
        this.verdict = verdict;
        this.detail = detail;
    }

    public static List<SubtaskInfo> toEntities(List<SubtaskResult> subtaskResults, List<String> details) {
        return IntStream.range(0, subtaskResults.size())
            .mapToObj(i -> toEntity(subtaskResults.get(i), details.get(i)))
            .toList();
    }

    private static SubtaskInfo toEntity(SubtaskResult subtaskResult, String detail) {
        return new SubtaskInfo(
            subtaskResult.getScore(),
            subtaskResult.getSubtask().getPerfectScore(),
            subtaskResult.getPassedTestcases(),
            subtaskResult.getTotalTestcases(),
            subtaskResult.getMaxExecutionTime(),
            subtaskResult.getMaxMemoryUsed(),
            subtaskResult.getVerdict(),
            detail
        );
    }
}
