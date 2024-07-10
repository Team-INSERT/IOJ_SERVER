package com.insert.ioj.domain.competition.presentation;

import com.insert.ioj.domain.competition.presentation.dto.req.SaveCompetitionRequest;
import com.insert.ioj.domain.competition.presentation.dto.res.ListCompetitionResponse;
import com.insert.ioj.domain.competition.service.ListCompetitionService;
import com.insert.ioj.domain.competition.service.SaveCompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Competition API")
@RequiredArgsConstructor
@RequestMapping("/competition")
@RestController
public class CompetitionController {
    private final ListCompetitionService listCompetitionService;
    private final SaveCompetitionService saveCompetitionService;

    @Operation(summary = "대회 리스트")
    @GetMapping
    public List<ListCompetitionResponse> listCompetition(Pageable pageable) {
        return listCompetitionService.execute(pageable);
    }

    @Operation(summary = "대회 생성")
    @PutMapping
    public Long saveCompetition(@RequestBody @Valid SaveCompetitionRequest request) {
        return saveCompetitionService.execute(request);
    }
}
