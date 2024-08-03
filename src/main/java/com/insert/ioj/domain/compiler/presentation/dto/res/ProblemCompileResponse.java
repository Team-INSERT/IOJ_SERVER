package com.insert.ioj.domain.compiler.presentation.dto.res;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problemContest.domain.ProblemContest;
import com.insert.ioj.domain.solve.domain.Solve;
import com.insert.ioj.domain.solveContest.domain.SolveContest;
import com.insert.ioj.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemCompileResponse {
    private Long id;
    private String compileStatus;
    private boolean isPass;
    private Verdict verdict;

    public ProblemCompileResponse(Long id, String compileStatus, boolean isPass, Verdict verdict) {
        this.id = id;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
        this.verdict = verdict;
    }

    public Solve toSolve(User user, Problem problem, String sourcecode) {
        return new Solve(
            user, problem, sourcecode, verdict
        );
    }

    public SolveContest toSolveContest(User user, ProblemContest problemContest, String sourcecode) {
        return new SolveContest(
            user, problemContest, sourcecode, compileStatus, isPass
        );
    }
}
