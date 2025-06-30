package com.insert.ioj.domain.submission.facade;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EntityFacade {
    private final CacheFacade cacheFacade;

    public Contest getContestById(Long id) {
        return cacheFacade.findContestById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_CONTEST));
    }

    public Submission getSubmissionById(UUID id) {
        return cacheFacade.findSubmissionById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_SUBMISSION));
    }

    public List<Testcase> getTestcasesByProblem(Problem problem) {
        return cacheFacade.findTestcasesById(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
    }

    public Problem getProblemById(Long id) {
        return cacheFacade.findProblemById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
    }
}
