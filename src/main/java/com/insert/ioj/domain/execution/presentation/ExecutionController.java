package com.insert.ioj.domain.execution.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Execution API")
@RequestMapping("/execution")
@RestController
public class ExecutionController {
    @Operation(summary = "UUID 생성")
    @GetMapping
    public String getSessionId() {
        return UUID.randomUUID().toString();
    }
}
