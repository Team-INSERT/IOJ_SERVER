package com.insert.ioj.domain.execution.language;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.global.constants.CommandConstants;
import com.insert.ioj.global.constants.ExtensionConstants;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.file.FileUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

@Getter
public class CExecution extends Execution {
    public CExecution(String sourcecode,
                      List<Testcase> testcases,
                      int timeLimit,
                      int memoryLimit) {
        super(sourcecode, testcases, timeLimit, memoryLimit);
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
            "timeout --signal=SIGTERM " + getTimeLimit() + " ./exec";
        String inputCommand = inputFileName == null ? "" : " < "+ inputFileName;
        return "#!/usr/bin/env bash\n" +
            "gcc main.c -o exec\n" +
            "ret=$?\n" +
            "if [ $ret -ne 0 ]\n" +
            "then\n" +
            "  exit 2\n" +
            "fi\n" +
            "ulimit -s " + getMemoryLimit() + "\n" +
            executionCommand + inputCommand + "\n" +
            "exit $?\n";
    }

    @Override
    protected void copySpecialFile() throws IOException {
        // null
    }

    @Override
    public Language getLanguage() {
        return Language.C;
    }
}
