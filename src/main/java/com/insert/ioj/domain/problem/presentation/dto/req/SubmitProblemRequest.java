package com.insert.ioj.domain.problem.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitProblemRequest {
    private Long id;
    private String sourcecode;
}
