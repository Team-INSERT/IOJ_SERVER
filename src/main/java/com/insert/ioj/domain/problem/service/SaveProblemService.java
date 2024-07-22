package com.insert.ioj.domain.problem.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import com.insert.ioj.domain.problemTestcase.domain.repository.ProblemTestcaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveProblemService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;

    @Transactional
    public Long execute(SaveProblemRequest request) {
        Problem problem = problemRepository.save(request.toProblem());
        List<Testcase> testcases = testcaseRepository.saveAll(request.toTestcaseList());
        for(Testcase testcase: testcases) {
            problemTestcaseRepository.save(
                new ProblemTestcase(problem, testcase)
            );
        }
        return problem.getId();
    }
}
