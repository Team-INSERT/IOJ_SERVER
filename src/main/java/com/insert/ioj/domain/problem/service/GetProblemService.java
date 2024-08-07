package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.req.TestcaseDto;
import com.insert.ioj.domain.problem.presentation.dto.res.ProblemResponse;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetProblemService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;

    public ProblemResponse execute(Long id) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<TestcaseDto> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM))
            .stream().map(TestcaseDto::new)
            .toList();
        return new ProblemResponse(problem, testcases);
    }
}
