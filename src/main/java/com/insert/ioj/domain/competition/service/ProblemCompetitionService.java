package com.insert.ioj.domain.competition.service;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.domain.repository.CompetitionRepository;
import com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionProblemResponse;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.problemCompetition.domain.repository.ProblemCompetitionRepository;
import com.insert.ioj.domain.solveCompetition.domain.SolveCompetition;
import com.insert.ioj.domain.solveCompetition.domain.repository.SolveCompetitionRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemCompetitionService {
    private final UserFacade userFacade;
    private final CompetitionRepository competitionRepository;
    private final ProblemCompetitionRepository problemCompetitionRepository;

    public List<ListCompetitionProblemResponse> execute(Long competitionId) {
        User user = userFacade.getCurrentUser();
        Competition competition = competitionRepository.findById(competitionId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_COMPETITION));
        competition.checkRole(user.getAuthority());
        return problemCompetitionRepository.getCompetitionProblemsWithStatus(competition, user);
    }
}
