package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.presentation.dto.req.TestcaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProblemResponse {
    private String title;
    private String content;
    private String inputContent;
    private int level;
    private int memoryLimit;
    private int timeLimit;
    private List<TestcaseDto> testcases;

    public ProblemResponse(Problem problem, List<TestcaseDto> testcases) {
        this.title = problem.getTitle();
        this.content = problem.getContent();
        this.inputContent = problem.getInputContent();
        this.level = problem.getLevel();
        this.memoryLimit = problem.getMemoryLimit();
        this.timeLimit = problem.getTimeLimit();
        this.testcases = testcases;
    }
}
