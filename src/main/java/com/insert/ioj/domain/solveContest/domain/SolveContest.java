package com.insert.ioj.domain.solveContest.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.solve.domain.Solve;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SolveContest extends Solve {
    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    public SolveContest(User user,
                        Contest contest,
                        Problem problem,
                        String sourcecode,
                        Verdict verdict,
                        Language language) {
        super(user, problem, sourcecode, verdict, language);
        this.contest = contest;
    }
}
