package com.insert.ioj.domain.contest.presentation;

import com.insert.ioj.domain.contest.presentation.dto.req.SaveContestRequest;
import com.insert.ioj.domain.contest.presentation.dto.req.SubmitContestRequest;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestAdminResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.RankingResponse;
import com.insert.ioj.domain.contest.service.GetContestService;
import com.insert.ioj.domain.contest.service.ListContestAdminService;
import com.insert.ioj.domain.contest.service.ListContestService;
import com.insert.ioj.domain.contest.service.RankingService;
import com.insert.ioj.domain.contest.service.SaveContestService;
import com.insert.ioj.domain.contest.service.SubmitContestService;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Contest API")
@RequiredArgsConstructor
@RequestMapping("/contest")
@RestController
public class ContestController {
    private final ListContestService listContestService;
    private final ListContestAdminService listContestAdminService;
    private final GetContestService getContestService;
    private final RankingService rankingService;
    private final SaveContestService saveContestService;
    private final SubmitContestService submitContestService;

    @Operation(summary = "대회 리스트")
    @GetMapping
    public List<ListContestResponse> listContest(Pageable pageable) {
        return listContestService.execute(pageable);
    }

    @Operation(summary = "어드민 대회 리스트")
    @GetMapping("/admin")
    public List<ListContestAdminResponse> listContestAdmin() {
        return listContestAdminService.execute();
    }

    @Operation(summary = "대회 상세 조회")
    @GetMapping("/{contestId}")
    public ContestResponse listContestProblem(
                @PathVariable(name = "contestId") Long id) {
        return getContestService.execute(id);
    }

    @Operation(summary = "대회 순위 조회")
    @GetMapping("/ranking/{contestId}")
    public List<RankingResponse> ranking(@PathVariable Long contestId) {
        return rankingService.execute(contestId);
    }

    @Operation(summary = "대회 생성")
    @PostMapping
    public Long saveContest(@RequestBody @Valid SaveContestRequest request) {
        return saveContestService.execute(request);
    }

    @Operation(summary = "대회 문제 제출")
    @PostMapping("/execution")
    public Verdict submitContest(@RequestBody SubmitContestRequest request) {
        return submitContestService.execute(request);
    }
}
