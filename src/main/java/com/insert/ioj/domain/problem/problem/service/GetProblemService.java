package com.insert.ioj.domain.problem.problem.service;

import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ProblemResponse;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.TestcaseResponse;
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
        List<TestcaseResponse> testcases = testcaseRepository.findAllByProblemAndExampleIsTrue(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM))
            .stream().map(TestcaseResponse::new)
            .toList();
        return new ProblemResponse(problem, testcases);
    }
}
