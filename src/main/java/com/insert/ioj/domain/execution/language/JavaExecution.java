package com.insert.ioj.domain.execution.language;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.Execution;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.file.FileUtil;
import lombok.Getter;

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
