package com.insert.ioj.infra.cmd.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProcessOutput {

    private String stdOut;

    private String stdErr;

    private int executionDuration;

    private int status;
}

