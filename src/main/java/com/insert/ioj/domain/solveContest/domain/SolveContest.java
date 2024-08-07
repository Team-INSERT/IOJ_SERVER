package com.insert.ioj.domain.solveContest.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SolveContest extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "contest")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "problem")
    private Problem problem;

    private String sourcecode;

    private String compileStatus;

    private boolean isPass;

    public SolveContest(User user,
                        Contest contest,
                        Problem problem,
                        String sourcecode,
                        String compileStatus,
                        boolean isPass) {
        this.user = user;
        this.contest = contest;
        this.problem = problem;
        this.sourcecode = sourcecode;
        this.compileStatus = compileStatus;
        this.isPass = isPass;
    }
}
