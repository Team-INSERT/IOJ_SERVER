package com.insert.ioj.domain.problem.presentation.dto.req;

import com.insert.ioj.domain.Testcase.domain.Testcase;
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

    public Testcase toEntity() {
        return new Testcase(input, output+"\n");
    }
}
