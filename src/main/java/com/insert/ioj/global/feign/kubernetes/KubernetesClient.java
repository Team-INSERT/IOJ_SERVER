package com.insert.ioj.global.feign.kubernetes;

import com.insert.ioj.global.feign.kubernetes.dto.req.KubernetesSubmissionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "KubernetesClient", url = "${kubernetes.url}")
public interface KubernetesClient {
    @PostMapping("/submission")
    void kubernetesSubmission(
        @RequestBody KubernetesSubmissionRequest request
    );
}
