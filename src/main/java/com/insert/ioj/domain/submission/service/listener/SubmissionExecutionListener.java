package com.insert.ioj.domain.submission.service.listener;

import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.global.feign.kubernetes.KubernetesClient;
import com.insert.ioj.global.feign.kubernetes.dto.req.KubernetesSubmissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SubmissionExecutionListener {
    private final KubernetesClient kubernetesClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(Execution execution) {
        kubernetesClient.kubernetesSubmission(
            new KubernetesSubmissionRequest(
                execution.getId(), execution.getMemoryLimit(), execution.getTimeLimit(), execution.getLanguage()
            ));
    }
}
