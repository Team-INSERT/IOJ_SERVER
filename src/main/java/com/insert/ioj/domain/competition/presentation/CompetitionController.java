package com.insert.ioj.domain.competition.presentation;

import com.insert.ioj.domain.competition.presentation.dto.req.SaveCompetitionRequest;
import com.insert.ioj.domain.competition.service.SaveCompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Competition API")
@RequiredArgsConstructor
@RequestMapping("/competition")
@RestController
public class CompetitionController {
    private final SaveCompetitionService saveCompetitionService;

    @Operation(summary = "대회 생성")
    @PutMapping
    public Long saveCompetition(@RequestBody @Valid SaveCompetitionRequest request) {
        return saveCompetitionService.execute(request);
    }
}
