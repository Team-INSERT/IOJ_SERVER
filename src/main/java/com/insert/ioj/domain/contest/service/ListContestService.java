package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.repository.ContestRepository;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListContestService {
    private final ContestRepository contestRepository;

    @Transactional(readOnly = true)
    public List<ListContestResponse> execute(Pageable pageable) {
        return contestRepository.findAll(pageable).stream()
            .map(ListContestResponse::new)
            .collect(Collectors.toList());
    }
}
