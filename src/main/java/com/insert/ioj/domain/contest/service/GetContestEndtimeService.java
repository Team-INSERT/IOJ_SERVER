package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.submission.facade.EntityFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class GetContestEndtimeService {
    private final EntityFacade entityFacade;

    public LocalDateTime execute(Long contestId) {
        return entityFacade.getContestById(contestId).getEndTime();
    }
}
