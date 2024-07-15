package com.insert.ioj.domain.competition.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.facade.CompetitionFacade;
import com.insert.ioj.domain.competition.presentation.dto.req.SubmitCompetitionRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.compiler.service.CompilerService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.problemCompetition.domain.repository.ProblemCompetitionRepository;
import com.insert.ioj.domain.problemTestcase.domain.ProblemTestcase;
import com.insert.ioj.domain.problemTestcase.domain.repository.ProblemTestcaseRepository;
import com.insert.ioj.domain.solveCompetition.domain.repository.SolveCompetitionRepository;
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
public class SubmitCompetitionService {
    private final CompilerService compilerService;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final SolveCompetitionRepository solveCompetitionRepository;
    private final CompetitionFacade competitionFacade;
    private final ProblemCompetitionRepository problemCompetitionRepository;
    private final ProblemRepository problemRepository;
    private final UserFacade userFacade;

    @Transactional
    public ProblemCompileResponse execute(SubmitCompetitionRequest req) throws Exception {
        User user = userFacade.getCurrentUser();
        Competition competition = competitionFacade.getCompetition(req.getCompetitionId());

        competition.checkRole(user.getAuthority());

        Problem problem = problemRepository.findById(req.getProblemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        ProblemCompetition problemCompetition =
            problemCompetitionRepository.findByCompetitionAndProblem(competition, problem);

        List<Testcase> testcases = saveTestcases(problem);

        ProblemCompileResponse res =
            compilerService.execute(problem, testcases, req.getSourcecode());

        solveCompetitionRepository.save(res.toSolveCompetition(user, problemCompetition, req.getSourcecode()));

        return res;
    }

    private List<Testcase> saveTestcases(Problem problem) {
        return
            problemTestcaseRepository.findAllByProblem(problem)
                .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM))
                .stream().map(ProblemTestcase::getTestcase).toList();
    }
}
