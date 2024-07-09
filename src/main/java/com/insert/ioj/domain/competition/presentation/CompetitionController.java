package com.insert.ioj.domain.competition.presentation;

import com.insert.ioj.domain.competition.presentation.dto.req.SaveCompetitionRequest;
import com.insert.ioj.domain.competition.service.SaveCompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/competition")
@RestController
public class CompetitionController {
    private final SaveCompetitionService saveCompetitionService;

    @PostMapping
    public Long saveCompetition(@RequestBody @Valid SaveCompetitionRequest request) {
        return saveCompetitionService.execute(request);
    }
}
