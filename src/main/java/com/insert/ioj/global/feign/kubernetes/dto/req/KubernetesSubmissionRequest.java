package com.insert.ioj.global.feign.kubernetes.dto.req;

import com.insert.ioj.domain.execution.language.Language;

public record KubernetesSubmissionRequest(
    String submission_id,
    int memory_limit,
    int time_limit,
    Language lang
) {
}
