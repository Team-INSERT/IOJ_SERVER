package com.insert.ioj.domain.compiler.presentation.dto.res;

import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.solve.domain.Solve;
import com.insert.ioj.domain.solveCompetition.domain.SolveCompetition;
import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemCompileResponse {
    private Long id;
    private String compileStatus;
    private boolean isPass;

    public ProblemCompileResponse(Long id, String compileStatus, boolean isPass) {
        this.id = id;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
    }

    public Solve toSolve(User user, Problem problem, String sourcecode) {
        return new Solve(
            user, problem, sourcecode, compileStatus, isPass
        );
    }

    public SolveCompetition toSolveCompetition(User user, ProblemCompetition problemCompetition, String sourcecode) {
        return new SolveCompetition(
            user, problemCompetition, sourcecode, compileStatus, isPass
        );
    }
}
