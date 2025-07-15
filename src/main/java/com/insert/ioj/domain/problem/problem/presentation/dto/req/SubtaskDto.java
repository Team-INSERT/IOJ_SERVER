package com.insert.ioj.domain.problem.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.subtask.domain.Subtask;

import java.util.ArrayList;
import java.util.List;

public record SubtaskDto(
    int score,
    String description,
    List<TestcaseDto> testcases
) {
    public List<Testcase> toTestcaseList(Problem problem, Subtask subtask, int orderIndex) {
        List<Testcase> testcaseList = new ArrayList<>();
        for(int i = 0; i < testcases.size(); i++) {
            testcaseList.add(testcases.get(i).toEntity(orderIndex + i, problem, subtask));
        }
        return testcaseList;
    }
}
