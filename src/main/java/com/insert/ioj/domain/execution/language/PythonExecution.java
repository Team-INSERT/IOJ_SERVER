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
    public PythonExecution(String sourcecode,
                           List<Testcase> testcases,
                           int timeLimit,
                           int memoryLimit) {
        super(sourcecode, testcases, timeLimit, memoryLimit);
    }

    @Override
    @SneakyThrows
    protected void createEntrypointFile(String inputFileName, String testcaseId) {
        String content = getCommand(inputFileName);

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
}
