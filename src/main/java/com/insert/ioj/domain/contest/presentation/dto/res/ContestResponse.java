package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.contest.domain.Contest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ContestResponse {
    private String title;
    private LocalDateTime endTime;
    private List<ListContestProblemResponse> problems;

    public ContestResponse(Contest contest, List<ListContestProblemResponse> problems) {
        this.title = contest.getTitle();
        this.endTime = contest.getEndTime();
        this.problems = problems;
    }
}
