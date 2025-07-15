package com.insert.ioj.domain.submission.facade;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.facade.dto.TestcaseCacheDto;
import com.insert.ioj.domain.subtask.domain.Subtask;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EntityFacade {
    private final CacheFacade cacheFacade;

    public Contest getContestById(Long id) {
        return cacheFacade.findContestById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_CONTEST));
    }

    public List<Testcase> getTestcasesByProblem(Long id) {
        TestcaseCacheDto[] testcaseDtos = cacheFacade.findTestcasesById(id);

        return Arrays.stream(testcaseDtos)
            .map(TestcaseCacheDto::toTestcase)
            .toList();
    }

    public Problem getProblemById(Long id) {
        return cacheFacade.findProblemById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
    }

    public User getUserById(Long id) {
        return cacheFacade.findUserById(id)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_USER));
    }

    public List<Subtask> getSubtasksByProblemId(Long id) {
        return cacheFacade.findSubtasksByProblemId(id);
    }
}
