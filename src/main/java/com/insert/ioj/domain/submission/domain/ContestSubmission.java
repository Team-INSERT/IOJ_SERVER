package com.insert.ioj.domain.submission.domain;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ContestSubmission extends Submission {
    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    public ContestSubmission(Language language, String sourcecode,
                      User user, Problem problem, Contest contest) {
        super(language, sourcecode, user, problem);
        this.contest = contest;
    }
}
