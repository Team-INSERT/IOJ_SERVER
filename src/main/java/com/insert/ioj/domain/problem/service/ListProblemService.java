package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.res.ListProblemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListProblemService {
    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public List<ListProblemResponse> execute(Pageable pageable) {
        return problemRepository.findAll(pageable).stream()
            .map(ListProblemResponse::new)
            .collect(Collectors.toList());
    }
}
