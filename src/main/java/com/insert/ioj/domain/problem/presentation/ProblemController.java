package com.insert.ioj.domain.problem.presentation;

import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.req.SubmitProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.res.ListProblemResponse;
import com.insert.ioj.domain.problem.service.ListProblemService;
import com.insert.ioj.domain.problem.service.SaveProblemService;
import com.insert.ioj.domain.problem.service.SubmitProblemService;
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
    private final SubmitProblemService submitProblemService;
    private final SaveProblemService saveProblemService;

    @Operation(summary = "문제 리스트")
    @GetMapping
    public List<ListProblemResponse> problemList(Pageable pageable) {
        return listProblemService.execute(pageable);
    }

    @Operation(summary = "문제 실행")
    @PostMapping
    public ProblemCompileResponse submitProblem(@RequestBody @Valid SubmitProblemRequest request) throws Exception {
        return submitProblemService.execute(request);
    }

    @Operation(summary = "문제 저장")
    @PutMapping
    public Long saveProblem(@RequestBody @Valid SaveProblemRequest request) {
        return saveProblemService.execute(request);
    }
}
