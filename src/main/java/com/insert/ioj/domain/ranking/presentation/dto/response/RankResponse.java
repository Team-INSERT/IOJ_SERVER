package com.insert.ioj.domain.ranking.presentation.dto.response;

import com.insert.ioj.domain.ranking.presentation.dto.response.element.ProblemOrderElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.RankElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.UserSubmissionElement;

import java.util.List;

public record RankResponse(
    List<ProblemOrderElement> problemOrders,
    List<RankElement> rankings,
    List<UserSubmissionElement> submissions
) {
}
