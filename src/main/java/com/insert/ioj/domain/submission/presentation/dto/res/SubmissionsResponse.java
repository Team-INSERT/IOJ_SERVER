package com.insert.ioj.domain.submission.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.submission.domain.ContestSubmission;

public record SubmissionsResponse(
    String id,
    Verdict verdict,
    int score,
    Language language
) {
    public static SubmissionsResponse toEntity(ContestSubmission submission) {
        return new SubmissionsResponse(
            submission.getId().toString(),
            submission.getVerdict(),
            submission.getTotalScore(),
            submission.getLanguage()
        );
    }
}
