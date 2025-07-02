package com.insert.ioj.domain.contest.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetContestDetailResponse {
    private LocalDateTime endtime;
    private List<ProblemIds> problemIds;

    public GetContestDetailResponse(LocalDateTime endtime, List<ProblemIds> problemIds) {
        this.endtime = endtime;
        this.problemIds = problemIds;
    }
}
