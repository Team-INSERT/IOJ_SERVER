package com.insert.ioj.domain.problem.presentation;

import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.CompileResponse;
import com.insert.ioj.domain.problem.presentation.dto.req.SaveProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.req.SubmitProblemRequest;
import com.insert.ioj.domain.problem.presentation.dto.res.SubmitProblemResponse;
import com.insert.ioj.domain.problem.service.SaveProblemService;
import com.insert.ioj.domain.problem.service.SubmitProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Problem API")
@RequiredArgsConstructor
@RequestMapping("/problem")
@RestController
public class ProblemController {
    private final SaveProblemService saveProblemService;
    private final SubmitProblemService submitProblemService;

    @Operation(summary = "문제 실행")
    @PostMapping
    public SubmitProblemResponse submitProblem(@RequestBody SubmitProblemRequest request) throws Exception {
        return submitProblemService.execute(request);
    }

    @Operation(summary = "문제 저장")
    @PostMapping("/save")
    public Long saveProblem(@RequestBody SaveProblemRequest request) {
        return saveProblemService.execute(request);
    }
}
