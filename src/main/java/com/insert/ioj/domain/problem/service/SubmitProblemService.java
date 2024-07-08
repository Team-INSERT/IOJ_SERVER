package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.compiler.service.CompilerService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.req.SubmitProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.res.SubmitProblemResponse;
import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import com.insert.ioj.domain.problemTestcase.domain.repository.ProblemTestcaseRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitProblemService {
    private final ProblemRepository problemRepository;
    private final CompilerService compilerService;
    private final ProblemTestcaseRepository problemTestcaseRepository;

    @Transactional
    public SubmitProblemResponse execute(SubmitProblemRequest request) throws Exception {
        Problem problem = problemRepository.findById(request.getId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        List<Testcase> testcases =
            problemTestcaseRepository.findAllByProblem(problem)
                .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM))
                .stream().map(ProblemTestcase::getTestcase).toList();
        return compilerService.execute(problem, testcases, request);
    }
}
