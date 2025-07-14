package com.insert.ioj.domain.ranking.presentation.dto.response.element;

import com.insert.ioj.domain.problem.problemContest.domain.ProblemContest;

public record ProblemOrderElement(
    Long problemId,
    Long orderId
) {
    public static ProblemOrderElement from(ProblemContest problemContest) {
        return new ProblemOrderElement(problemContest.getProblem().getId(), problemContest.getOrderId());
    }
}
