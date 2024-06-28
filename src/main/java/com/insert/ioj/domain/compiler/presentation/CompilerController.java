package com.insert.ioj.domain.compiler.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Compiler API")
@RequiredArgsConstructor
@RequestMapping("/compiler")
@RestController
public class CompilerController {
    @GetMapping("/python")
    public void pythonCompiler() {

    }
}
