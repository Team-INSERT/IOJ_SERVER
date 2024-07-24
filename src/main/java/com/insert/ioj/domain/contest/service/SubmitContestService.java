package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.facade.ContestFacade;
import com.insert.ioj.domain.contest.presentation.dto.req.SubmitContestRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.compiler.service.CompilerService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problemContest.domain.ProblemContest;
import com.insert.ioj.domain.problemContest.domain.repository.ProblemContestRepository;
import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import com.insert.ioj.domain.problemTestcase.domain.repository.ProblemTestcaseRepository;
import com.insert.ioj.domain.solveContest.domain.repository.SolveContestRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitContestService {
    private final CompilerService compilerService;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final SolveContestRepository solveContestRepository;
    private final ContestFacade contestFacade;
    private final ProblemContestRepository problemContestRepository;
    private final ProblemRepository problemRepository;
    private final UserFacade userFacade;

    @Transactional
    public ProblemCompileResponse execute(SubmitContestRequest req) throws Exception {
        User user = userFacade.getCurrentUser();
        Contest contest = contestFacade.getContest(req.getContestId());

        contest.checkRole(user.getAuthority());

        Problem problem = problemRepository.findById(req.getProblemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        ProblemContest problemContest =
            problemContestRepository.findByContestAndProblem(contest, problem);

        List<Testcase> testcases = saveTestcases(problem);

        ProblemCompileResponse res =
            compilerService.execute(problem, testcases, req.getSourcecode());

        solveContestRepository.save(res.toSolveContest(user, problemContest, req.getSourcecode()));

        return res;
    }

    private List<Testcase> saveTestcases(Problem problem) {
        return
            problemTestcaseRepository.findAllByProblem(problem)
                .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM))
                .stream().map(ProblemTestcase::getTestcase).toList();
    }
}
