package com.insert.ioj.global.feign.kubernetes.dto.req;

import com.insert.ioj.domain.execution.language.Language;

public record KubernetesSubmissionRequest(
    String submissionId,
    int memoryLimit,
    int timeLimit,
    Language language
) {
}
