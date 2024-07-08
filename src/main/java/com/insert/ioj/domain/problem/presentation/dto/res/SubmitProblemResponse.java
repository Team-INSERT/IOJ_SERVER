package com.insert.ioj.domain.problem.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.solve.domain.Solve;
import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitProblemResponse {
    private Long id;
    private String compileStatus;
    private boolean isPass;

    public SubmitProblemResponse(Long id, String compileStatus, boolean isPass) {
        this.id = id;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
    }

    public Solve toEntity(User user, Problem problem, String sourcecode) {
        return new Solve(
            user, problem, sourcecode, compileStatus, isPass
        );
    }
}
