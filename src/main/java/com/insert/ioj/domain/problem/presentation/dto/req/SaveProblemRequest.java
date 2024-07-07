package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveProblemRequest {
    private String title;
    private String content;
    private int level;
    private int memoryLimit;
    private int timeLimit;
    private List<TestcaseDto> testcases;

    public Problem toProblem() {
        return new Problem(
            title, content, level, memoryLimit, timeLimit
        );
    }

    public List<Testcase> toTestcaseList() {
        List<Testcase> testcaseList = new ArrayList<>();
        for(TestcaseDto testcase: testcases) {
            testcaseList.add(testcase.toEntity());
        }
        return testcaseList;
    }
}
