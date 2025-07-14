package com.insert.ioj.domain.submission.presentation;

import com.insert.ioj.domain.submission.presentation.dto.req.SubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.req.TestcasesSubmissionRequest;
import com.insert.ioj.domain.submission.presentation.dto.res.SubmissionResponse;
import com.insert.ioj.domain.submission.presentation.dto.res.TestcaseSubmissionStatusResponse;
import com.insert.ioj.domain.submission.service.SubmissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Tag(name = "Submission API")
@RequiredArgsConstructor
@RequestMapping("/submissions")
@RestController
public class SubmissionController {
    private final SubmissionService submissionService;

    @GetMapping("/{submission-id}/status")
    public SubmissionResponse getSubmissionStatus(
        @PathVariable("submission-id") UUID id
    ) {
        return submissionService.submissionStatus(id);
    }

    @GetMapping("/{testcaseSubmission-id}/status/testcase")
    public List<TestcaseSubmissionStatusResponse> getTestcaseSubmissionStatus(
        @PathVariable("testcaseSubmission-id") UUID id
    ) {
        return submissionService.testcaseSubmissionStatus(id);
    }

    @PostMapping("/testcases")
    public UUID createTestcasesSubmission(
        @RequestBody @Valid TestcasesSubmissionRequest request
    ) throws IOException {
        return submissionService.testcasesSubmission(request);
    }

    @PostMapping
    public UUID create(
        @RequestBody @Valid SubmissionRequest request
    ) throws IOException {
        return submissionService.create(request);
    }

    @GetMapping("/{submission-id}/complete")
    public void complete(
        @PathVariable("submission-id") String id,
        @RequestParam String status
    ) throws IOException {
        submissionService.complete(id, status);
    }
}
