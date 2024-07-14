package com.insert.ioj.domain.competition.facade;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.domain.repository.CompetitionRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompetitionFacade {
    private final CompetitionRepository competitionRepository;

    public Competition getCompetition(Long id) {
        return competitionRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_COMPETITION));
    }
}
