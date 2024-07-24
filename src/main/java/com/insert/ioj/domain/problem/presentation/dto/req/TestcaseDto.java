package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.problem.domain.Problem;
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

    public Testcase toEntity(Problem problem) {
        return new Testcase(input, output+"\n", problem);
    }
}
