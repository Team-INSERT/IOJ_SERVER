package com.insert.ioj.domain.contest.presentation.dto.req;

import com.insert.ioj.domain.execution.language.Language;
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

    @NotNull(message = "language가 비어있습니다.")
    private Language language;
}
