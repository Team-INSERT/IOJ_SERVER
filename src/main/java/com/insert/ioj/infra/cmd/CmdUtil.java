package com.insert.ioj.infra.cmd;

import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CmdUtil {
    public static String readOutput(BufferedReader reader) throws IOException {
        String line;
        StringBuilder builder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public static ProcessOutput executeProcess(String[] command, long timeout) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();
            long executionStartTime = System.currentTimeMillis();

            process.waitFor(timeout, TimeUnit.SECONDS);
            long executionEndTime = System.currentTimeMillis();

            int status = process.exitValue();

            BufferedReader containerOutputReader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
            String stdOut = CmdUtil.readOutput(containerOutputReader);

            BufferedReader containerErrorReader =
                new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String stdErr = CmdUtil.readOutput(containerErrorReader);

            return ProcessOutput.builder()
                .stdOut(stdOut)
                .stdErr(stdErr)
                .executionDuration((int)(executionEndTime - executionStartTime))
                .status(status)
                .build();
        } catch (IOException | InterruptedException e) {
            throw new IojException(ErrorCode.PROCESS_EXECUTION);
        }
    }
}
