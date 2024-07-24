package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.domain.Problem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

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

    @Range(min = 1, max = 5, message = "level은 1혹은 5사이어야 합니다.")
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
