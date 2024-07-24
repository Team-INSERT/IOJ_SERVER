package com.insert.ioj.domain.contest.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitContestRequest {
    @NotNull(message = "contestId가 비어있습니다.")
    private Long contestId;

    @NotNull(message = "problemId가 비어있습니다.")
    private Long problemId;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;
}
