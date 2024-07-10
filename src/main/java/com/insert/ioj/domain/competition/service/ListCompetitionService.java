package com.insert.ioj.domain.competition.service;

import com.insert.ioj.domain.competition.domain.repository.CompetitionRepository;
import com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListCompetitionService {
    private final CompetitionRepository competitionRepository;

    public List<ListCompetitionResponse> execute(Pageable pageable) {
        return competitionRepository.findAll(pageable).stream()
            .map(ListCompetitionResponse::new)
            .collect(Collectors.toList());
    }
}
