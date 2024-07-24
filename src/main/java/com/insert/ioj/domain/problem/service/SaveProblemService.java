package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.req.SaveProblemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveProblemService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;

    @Transactional
    public Long execute(SaveProblemRequest request) {
        Problem problem = problemRepository.save(request.toProblem());
        testcaseRepository.saveAll(request.toTestcaseList(problem));
        return problem.getId();
    }
}
