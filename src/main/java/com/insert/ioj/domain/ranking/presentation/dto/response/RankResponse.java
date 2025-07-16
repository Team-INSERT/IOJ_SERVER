package com.insert.ioj.domain.ranking.presentation.dto.response;

import com.insert.ioj.domain.ranking.presentation.dto.response.element.ProblemOrderElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.RankElement;
import com.insert.ioj.domain.ranking.presentation.dto.response.element.UserSubmissionElement;

import java.time.LocalDateTime;
import java.util.List;

public record RankResponse(
    String title,
    LocalDateTime startTime,
    List<ProblemOrderElement> problemOrders,
    List<RankElement> rankings,
    List<UserSubmissionElement> submissions
) {
}
