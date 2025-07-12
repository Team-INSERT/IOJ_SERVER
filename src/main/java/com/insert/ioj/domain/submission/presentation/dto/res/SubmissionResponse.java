package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.subtask.domain.SubtaskResult;

import java.util.List;

public record SubmissionResponse(
    Verdict verdict,
    List<SubtaskInfo> subtaskInfos
) {
    public static SubmissionResponse of(
        Submission submission, List<SubtaskResult> subtaskResults
    ) {
        return new SubmissionResponse(
            submission.getVerdict(),
            SubtaskInfo.toEntities(subtaskResults)
        );
    }
}
