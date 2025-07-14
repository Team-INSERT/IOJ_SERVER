package com.insert.ioj.domain.contest.service;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.contest.presentation.dto.res.GetContestDetailResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ProblemIds;
import com.insert.ioj.domain.problem.problem.domain.repository.CustomProblemRepository;
import com.insert.ioj.domain.submission.facade.EntityFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetContestDetailService {
    private final EntityFacade entityFacade;
    private final CustomProblemRepository customProblemRepository;

    public GetContestDetailResponse execute(Long contestId) {
        Contest contest = entityFacade.getContestById(contestId);

        List<ProblemIds> problemIds = customProblemRepository.getContestProblems(contest).stream()
            .map(problem -> new ProblemIds(problem.getProblem().getId(), problem.getOrderId()))
            .toList();

        return new GetContestDetailResponse(contest.getEndTime(), problemIds);
    }
}
