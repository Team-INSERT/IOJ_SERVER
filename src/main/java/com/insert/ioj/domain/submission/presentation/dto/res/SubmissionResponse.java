package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.subtask.domain.SubtaskResult;

import java.time.LocalDateTime;
import java.util.List;

public record SubmissionResponse(
    Verdict verdict,
    LocalDateTime submittedAt,
    List<SubtaskInfo> subtaskInfos,
    String compilationDetail
) {
    public static SubmissionResponse of(
        Submission submission, List<SubtaskResult> subtaskResults, List<String> details, String compilationDetail
    ) {
        return new SubmissionResponse(
            submission.getVerdict(),
            submission.getCreatedAt(),
            SubtaskInfo.toEntities(subtaskResults, details),
            compilationDetail
        );
    }
}
