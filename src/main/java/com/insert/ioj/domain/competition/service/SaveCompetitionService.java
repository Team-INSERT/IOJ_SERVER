package com.insert.ioj.domain.competition.service;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.competition.domain.repository.CompetitionRepository;
import com.insert.ioj.domain.competition.presentation.dto.req.SaveCompetitionRequest;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problemCompetition.domain.ProblemCompetition;
import com.insert.ioj.domain.problemCompetition.domain.repository.ProblemCompetitionRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveCompetitionService {
    private final CompetitionRepository competitionRepository;
    private final ProblemRepository problemRepository;
    private final ProblemCompetitionRepository problemCompetitionRepository;

    @Transactional
    public Long execute(SaveCompetitionRequest request) {
        Competition competition = competitionRepository.save(request.toEntity());
        List<Problem> problems = problemRepository.findAllById(request.getProblems());
        for(Problem problem: problems) {
            problemCompetitionRepository.save(
                new ProblemCompetition(problem, competition)
            );
        }
        return competition.getId();
    }
}
