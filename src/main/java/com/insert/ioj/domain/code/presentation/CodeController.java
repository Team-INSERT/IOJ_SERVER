package com.insert.ioj.domain.code.presentation;

import com.insert.ioj.global.constants.CodeConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Code API")
@RequestMapping("/code")
@RestController
public class CodeController {
    @Operation(summary = "c언어의 boilerplate 반환")
    @GetMapping("/c")
    public String getC() {
        return CodeConstants.C;
    }

    @Operation(summary = "cpp언어의 boilerplate 반환")
    @GetMapping("/cpp")
    public String getCpp() {
        return CodeConstants.CPP;
    }

    @Operation(summary = "java언어의 boilerplate 반환")
    @GetMapping("/java")
    public String getJava() {
        return CodeConstants.JAVA;
    }

    @Operation(summary = "python언어의 boilerplate 반환")
    @GetMapping("/python")
    public String getPython() {
        return CodeConstants.PYTHON;
    }
}
