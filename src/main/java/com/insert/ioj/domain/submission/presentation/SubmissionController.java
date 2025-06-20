package com.insert.ioj.domain.submission.presentation;

import com.insert.ioj.domain.submission.presentation.dto.req.SubmissionRequest;
import com.insert.ioj.domain.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/submissions")
@RestController
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public UUID create(
        @RequestBody @Valid SubmissionRequest request
    ) throws IOException {
        return submissionService.create(request);
    }

    @GetMapping("/{submission-id}/complete")
    public void complete(
        @PathVariable("submission-id") String id
    ) throws IOException {
        submissionService.complete(id);
    }

    @GetMapping("/{submission-id}/complete/compile")
    public void completeCompile(
        @PathVariable("submission-id") String id
    ) {
        submissionService.completeCompile(id);
    }
}
