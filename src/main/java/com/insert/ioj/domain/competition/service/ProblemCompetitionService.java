package com.insert.ioj.domain.competition.service;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.facade.CompetitionFacade;
import com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionProblemResponse;
import com.insert.ioj.domain.problemCompetition.domain.repository.ProblemCompetitionRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemCompetitionService {
    private final UserFacade userFacade;
    private final CompetitionFacade competitionFacade;
    private final ProblemCompetitionRepository problemCompetitionRepository;

    public List<ListCompetitionProblemResponse> execute(Long competitionId) {
        User user = userFacade.getCurrentUser();
        Competition competition = competitionFacade.getCompetition(competitionId);
        competition.checkRole(user.getAuthority());
        return problemCompetitionRepository.getCompetitionProblemsWithStatus(competition, user);
    }
}
