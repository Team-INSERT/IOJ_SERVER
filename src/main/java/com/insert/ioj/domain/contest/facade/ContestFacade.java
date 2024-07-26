package com.insert.ioj.domain.contest.facade;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContestFacade {
    private final ContestRepository contestRepository;

    public Contest getContest(Long id) {
        return contestRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_CONTEST));
    }
}
