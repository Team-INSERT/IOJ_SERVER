package com.insert.ioj.domain.problem.problem.service;

import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ProblemResponse;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.SubtaskResponse;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.TestcaseResponse;
import com.insert.ioj.domain.submission.facade.EntityFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetProblemService {
    private final EntityFacade entityFacade;
    private final TestcaseRepository testcaseRepository;

    public ProblemResponse execute(Long id) {
        Problem problem = entityFacade.getProblemById(id);

        List<SubtaskResponse> subtasks = entityFacade.getSubtasksByProblemId(id).stream()
            .map(SubtaskResponse::from)
            .toList();

        List<TestcaseResponse> testcases = testcaseRepository.findAllByProblemAndExampleIsTrue(problem)
            .stream().map(TestcaseResponse::new)
            .toList();

        return new ProblemResponse(problem, testcases, subtasks);
    }
}
