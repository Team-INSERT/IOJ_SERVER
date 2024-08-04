package com.insert.ioj.domain.execution.service;

import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.docker.DockerUtil;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {
    private static final String TEST_CASE_ID_ENV_VARIABLE = "TEST_CASE_ID";

    public ProcessOutput run(Execution execution, String testcaseId) {
        return DockerUtil.runContainer(
            execution.getImageName(), TEST_CASE_ID_ENV_VARIABLE + "=" + testcaseId);
    }
}
