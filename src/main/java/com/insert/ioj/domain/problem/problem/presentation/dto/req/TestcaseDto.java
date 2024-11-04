package com.insert.ioj.domain.problem.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestcaseDto {
    @NotNull(message = "input이 비어있습니다.")
    private String input;

    @NotNull(message = "output이 비어있습니다.")
    private String output;

    @NotNull(message = "example이 비어있습니다.")
    private boolean example;

    public Testcase toEntity(Problem problem) {
        return new Testcase(input, output+"\n", example, problem);
    }

    public TestcaseDto(Testcase testcase) {
        this.input = testcase.getInput();
        this.output = testcase.getOutput();
    }
}
