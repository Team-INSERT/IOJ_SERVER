package com.insert.ioj.domain.execution.language;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.global.constants.ExtensionConstants;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.file.FileUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

@Getter
public class JavaExecution extends Execution {
    public JavaExecution(String sourcecode,
                         List<Testcase> testcases,
                         int timeLimit,
                         int memoryLimit) {
        super(sourcecode, testcases, timeLimit, memoryLimit);
    }

    @Override
    @SneakyThrows
    protected void createEntrypointFile(String inputFileName, String testcaseId) {
        String fileName = getLanguage().getSourcecodeFileName();
        String prefixName = fileName.substring(0,fileName.length() - 5);
        String content = getCommand(inputFileName, prefixName);
        testcaseId = testcaseId == null ? "execution" : testcaseId;

        String path = getPath()
            + "/"
            + FileConstants.ENTRYPOINT_FILE_NAME_PREFIX
            + testcaseId
            + ExtensionConstants.ENTRYPOINT_EXTENSION;

        FileUtil.saveUploadedFiles(content, path);
    }

    private String getCommand(String inputFileName, String prefixName) {
        String executionCommand =
            "timeout --signal=SIGTERM " + getTimeLimit() + " java -Djava.security.manager " +
            "-Djava.security.policy=./security.policy " + prefixName;
        String inputCommand = inputFileName == null ? "" : " < " + inputFileName;
        return "#!/usr/bin/env bash\n" +
            "javac main.java" + "\n" +
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
        FileUtil.copyFile(getLanguage()
                .getFolderName()
                .concat("/" + FileConstants.JAVA_SECURITY_POLICY_FILE_NAME),
            getPath().concat("/" + FileConstants.JAVA_SECURITY_POLICY_FILE_NAME));
    }

    @Override
    public Language getLanguage() {
        return Language.JAVA;
    }
}
