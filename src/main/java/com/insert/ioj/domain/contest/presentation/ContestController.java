package com.insert.ioj.domain.contest.presentation;

import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.contest.presentation.dto.req.SaveContestRequest;
import com.insert.ioj.domain.contest.presentation.dto.req.SubmitContestRequest;
import com.insert.ioj.domain.contest.presentation.dto.res.ContestResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestAdminResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestProblemResponse;
import com.insert.ioj.domain.contest.presentation.dto.res.ListContestResponse;
import com.insert.ioj.domain.contest.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contest API")
@RequiredArgsConstructor
@RequestMapping("/contest")
@RestController
public class ContestController {
    private final ListContestService listContestService;
    private final ListContestAdminService listContestAdminService;
    private final GetContestService getContestService;
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

    @Operation(summary = "대회 생성")
    @PostMapping
    public Long saveContest(@RequestBody @Valid SaveContestRequest request) {
        return saveContestService.execute(request);
    }

    @Operation(summary = "대회 문제 제출")
    @PostMapping("/execution")
    public ProblemCompileResponse submitContest(@RequestBody SubmitContestRequest request) throws Exception {
        return submitContestService.execute(request);
    }
}
