package com.insert.ioj.domain.problem.problem.presentation;

import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.ExecutionProblemRequest;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ListProblemResponse;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.ProblemResponse;
import com.insert.ioj.domain.problem.problem.presentation.dto.res.TestcasesResponse;
import com.insert.ioj.domain.problem.problem.service.ExecutionProblemService;
import com.insert.ioj.domain.problem.problem.service.GetProblemService;
import com.insert.ioj.domain.problem.problem.service.ListProblemService;
import com.insert.ioj.domain.problem.problem.service.SaveProblemService;
import com.insert.ioj.domain.problem.problem.service.VerifyTestcasesService;
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

@Tag(name = "Problem API")
@RequiredArgsConstructor
@RequestMapping("/problem")
@RestController
public class ProblemController {
    private final ListProblemService listProblemService;
    private final GetProblemService getProblemService;
    private final SaveProblemService saveProblemService;
    private final VerifyTestcasesService verifyTestcasesService;
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

    @Operation(summary = "테스트 케이스 검증")
    @PostMapping("/submit/testcases")
    public List<TestcasesResponse> verifyTestcases(@RequestBody @Valid ExecutionProblemRequest request) {
        return verifyTestcasesService.execute(request);
    }

    @Operation(summary = "문제 저장")
    @PostMapping
    public Long saveProblem(@RequestBody @Valid SaveProblemRequest request) {
        return saveProblemService.execute(request);
    }
}
