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
    private String outputContent;
    private int level;
    private int memoryLimit;
    private int timeLimit;
    private List<TestcaseResponse> testcases;

    public ProblemResponse(Problem problem, List<TestcaseResponse> testcases) {
        this.title = problem.getTitle();
        this.content = problem.getContent();
        this.inputContent = problem.getInputContent();
        this.outputContent = problem.getOutputContent();
        this.level = problem.getLevel();
        this.memoryLimit = problem.getMemoryLimit();
        this.timeLimit = problem.getTimeLimit();
        this.testcases = testcases;
    }
}
