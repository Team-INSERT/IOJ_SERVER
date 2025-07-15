package com.insert.ioj.domain.problem.problem.service;

import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.SubtaskDto;
import com.insert.ioj.domain.subtask.domain.Subtask;
import com.insert.ioj.domain.subtask.domain.repository.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveProblemService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final SubtaskRepository subtaskRepository;

    @Transactional
    public Long execute(SaveProblemRequest request) {
        Problem problem = problemRepository.save(request.toProblem());

        int orderIndex = 0;
        for (SubtaskDto subtaskDto: request.getSubtaskDtos()) {
            Subtask subtask = new Subtask(
                subtaskDto.score(), subtaskDto.testcases().size(), subtaskDto.description(), problem
            );

            subtaskRepository.save(subtask);
            testcaseRepository.saveAll(subtaskDto.toTestcaseList(problem, subtask, orderIndex));
            orderIndex += subtaskDto.testcases().size();
        }

        return problem.getId();
    }
}
