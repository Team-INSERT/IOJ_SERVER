package com.insert.ioj.domain.ranking.presentation;

import com.insert.ioj.domain.contest.service.RankingService;
import com.insert.ioj.domain.ranking.presentation.dto.response.RankResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ranking API")
@RequiredArgsConstructor
@RequestMapping("/ranking")
@RestController
public class RankingController {
    private final RankingService rankingService;

    @Operation(summary = "랭킹 조회 api입니다.")
    @GetMapping("/{contest-id}")
    public RankResponse getRanking(@PathVariable("contest-id") Long id) {
        return rankingService.ranking(id);
    }
}
