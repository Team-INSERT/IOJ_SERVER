package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RankingResponse {
    private String nickname;
    private List<ProblemStatusResponse> problemStatuses;

    public RankingResponse(String nickname, List<ProblemStatusResponse> problemStatuses) {
        this.nickname = nickname;
        this.problemStatuses = problemStatuses;
    }
}
