package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.domain.Problem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveProblemRequest {
    @Size(max = 22, message = "title은 공백 포함 22자까지만 가능합니다.")
    @NotNull(message = "title이 비어있습니다.")
    private String title;

    @NotNull(message = "content가 비어있습니다.")
    private String content;

    @Min(value = 1, message = "level은 1보다 커야 합니다.")
    @Max(value = 5, message = "level은 5보다 작아야 합니다.")
    @NotNull(message = "level이 비어있습니다.")
    private Integer level;

    @Positive(message = "양수 값만 입력할 수 있습니다.")
    @NotNull(message = "memeoryLimit이 비어있습니다.")
    private Integer memoryLimit;

    @Positive(message = "양수 값만 입력할 수 있습니다.")
    @NotNull(message = "timeLimit이 비어있습니다.")
    private Integer timeLimit;

    @Valid
    @NotNull(message = "testcases가 비어있습니다.")
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
