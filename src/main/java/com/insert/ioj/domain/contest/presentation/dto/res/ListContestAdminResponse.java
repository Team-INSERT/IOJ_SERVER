package com.insert.ioj.domain.contest.presentation.dto.res;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.user.domain.type.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ListContestAdminResponse {
    private Long id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> problemIds = new ArrayList<>();
    private Authority authority;

    public ListContestAdminResponse(Contest contest, List<Problem> problems) {
        this.id = contest.getId();
        this.title = contest.getTitle();
        this.startTime = contest.getStartTime();
        this.endTime = contest.getEndTime();
        this.authority = contest.getAuthority();
        problems.forEach(problem -> problemIds.add(problem.getId()));
    }
}
