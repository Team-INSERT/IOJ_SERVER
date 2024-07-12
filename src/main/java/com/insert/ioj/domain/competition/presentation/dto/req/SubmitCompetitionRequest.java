package com.insert.ioj.domain.competition.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitCompetitionRequest {
    @NotNull(message = "competitionId가 비어있습니다.")
    private Long competitionId;

    @NotNull(message = "problemId가 비어있습니다.")
    private Long problemId;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;
}
