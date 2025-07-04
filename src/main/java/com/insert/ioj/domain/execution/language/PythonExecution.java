package com.insert.ioj.domain.execution.language;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.global.constants.ExtensionConstants;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.file.FileUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
public class PythonExecution extends Execution {
    public PythonExecution(String id,
                           String sourcecode,
                           List<Testcase> testcases,
                           int timeLimit,
                           int memoryLimit,
                           String volumePath) {
        super(id, sourcecode, testcases, timeLimit, memoryLimit, volumePath);
    }

    @Override
    @SneakyThrows
    protected void createEntrypointFile(String inputFileName, String testcaseId) {
        String content = getCommand(inputFileName);
        testcaseId = testcaseId == null ? "execution" : testcaseId;

        String path = getPath()
            + "/"
            + FileConstants.ENTRYPOINT_FILE_NAME_PREFIX
            + testcaseId
            + ExtensionConstants.ENTRYPOINT_EXTENSION;

        FileUtil.saveUploadedFiles(content, path);
    }

    private String getCommand(String inputFileName) {
        String executionCommand =
            "timeout --signal=SIGTERM " + getTimeLimit() + " python3 main.py";
        String inputCommand = inputFileName == null ? "" : " < " + inputFileName;
        return "#!/usr/bin/env bash\n" +
            "ulimit -s " + getMemoryLimit() + "\n" +
            executionCommand + inputCommand + "\n" +
            "exit $?\n";
    }

    @Override
    protected void copySpecialFile() {
        // null
    }

    @Override
    public Language getLanguage() {
        return Language.PYTHON;
    }

    @Override
    public int getTimeLimit() {
        return timeLimit*3+2;
    }

    @Override
    public int getMemoryLimit() {
        return memoryLimit * 2 + (32 * 1024); // 기본 메모리 * 2 + 32MB
    }
}
