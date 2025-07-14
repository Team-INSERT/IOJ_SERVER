package com.insert.ioj.domain.ranking.presentation.dto.response.element;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problemscore.domain.ProblemScore;

public record SubmissionDetailElement(
    Long problemId,
    int score,
    Verdict verdict
) {
    public static SubmissionDetailElement from(ProblemScore problemScore) {
        return new SubmissionDetailElement(
            problemScore.getProblemId(), problemScore.getScore(), problemScore.getVerdict()
        );
    }
}
