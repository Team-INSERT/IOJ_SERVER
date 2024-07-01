package com.insert.ioj.domain.compiler.presentation;

import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import com.insert.ioj.domain.compiler.service.CompilerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Compiler API")
@RequiredArgsConstructor
@RequestMapping("/compiler")
@RestController
public class CompilerController {
    private final CompilerService compilerService;

    @PostMapping("/python")
    public void pythonCompiler(@RequestBody CompileCodeRequest request) throws IOException {
        compilerService.execute(request);
    }
}
