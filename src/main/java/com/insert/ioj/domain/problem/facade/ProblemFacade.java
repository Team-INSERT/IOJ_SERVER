package com.insert.ioj.domain.problem.facade;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.CustomProblemRepository;
import com.insert.ioj.domain.problem.presentation.dto.res.ProblemStatusDto;
import com.insert.ioj.domain.solve.contest.SolveContest;
import com.insert.ioj.domain.solve.contest.repository.CustomSolveContestRepository;
import com.insert.ioj.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProblemFacade {
    private final CustomProblemRepository customProblemRepository;
    private final CustomSolveContestRepository customSolveContestRepository;

    public List<ProblemStatusDto> getProblemStatuses(Contest contest, User user) {
        List<Problem> problems = customProblemRepository.getContestProblems(contest);
        List<SolveContest> solveContests = customSolveContestRepository.getUserSolveContest(user, contest);

        Map<Long, SolveContest> latestSolveContests = new HashMap<>();
        for (SolveContest solveContest : solveContests) {
            latestSolveContests.putIfAbsent(solveContest.getProblem().getId(), solveContest);
        }

        return getProblemStatus(problems, latestSolveContests);
    }

    private static List<ProblemStatusDto> getProblemStatus(List<Problem> problems,
                                                           Map<Long, SolveContest> latestSolveContests) {
        List<ProblemStatusDto> problemStatuses = new ArrayList<>();
        for (Problem problem : problems) {
            SolveContest solveContest = latestSolveContests.get(problem.getId());
            String status = "solved";
            if (solveContest == null) {
                status = "unsolved";
            } else if (solveContest.getVerdict() != Verdict.ACCEPTED) {
                status = "failed";
            }

            LocalDateTime solveTime = solveContest != null ? solveContest.getCreatedAt() : null;
            problemStatuses.add(new ProblemStatusDto(problem, status, solveTime));
        }
        return problemStatuses;
    }
}
