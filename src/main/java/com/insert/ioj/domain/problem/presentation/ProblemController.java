package com.insert.ioj.domain.problem.presentation;

import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.presentation.dto.req.ExecutionProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.req.SubmitProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.res.ListProblemResponse;
import com.insert.ioj.domain.problem.presentation.dto.res.ProblemResponse;
import com.insert.ioj.domain.problem.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Problem API")
@RequiredArgsConstructor
@RequestMapping("/problem")
@RestController
public class ProblemController {
    private final ListProblemService listProblemService;
    private final GetProblemService getProblemService;
    private final SubmitProblemService submitProblemService;
    private final SaveProblemService saveProblemService;
    private final ExecutionProblemService executionProblemService;

    @Operation(summary = "문제 리스트")
    @GetMapping
    public List<ListProblemResponse> problemList(Pageable pageable) {
        return listProblemService.execute(pageable);
    }

    @Operation(summary = "문제 상세 조회")
    @GetMapping("/{id}")
    public ProblemResponse getProblem(@PathVariable Long id) {
        return getProblemService.execute(id);
    }

    @Operation(summary = "다양한 언어 문제 제출")
    @PostMapping("/submit")
    public Verdict exe(@RequestBody @Valid ExecutionProblemRequest request) {
        return executionProblemService.execute(request);
    }

    @Operation(summary = "문제 실행")
    @PostMapping("/execution")
    public ProblemCompileResponse submitProblem(@RequestBody @Valid SubmitProblemRequest request) throws Exception {
        return submitProblemService.execute(request);
    }

    @Operation(summary = "문제 저장")
    @PostMapping
    public Long saveProblem(@RequestBody @Valid SaveProblemRequest request) {
        return saveProblemService.execute(request);
    }
}
