package com.insert.ioj.domain.execution;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.language.Language;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public abstract class Execution {
    private static final String EXECUTION_FOLDER_PREFIX_NAME = "execution-";

    private String id;
    private String sourcecode;
    private List<Testcase> testcases;
    private int timeLimit;
    private int memoryLimit;
    private String path;

    protected Execution(String sourcecode,
                        List<Testcase> testcases,
                        int timeLimit,
                        int memoryLimit) {
        this.id = UUID.randomUUID().toString();
        this.sourcecode = sourcecode;
        this.testcases = testcases;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.path = getLanguage().getFolderName() + "/" + getExecutionFolderName();
    }

    public String getExecutionFolderName() {
        return EXECUTION_FOLDER_PREFIX_NAME + id;
    }

    public abstract Language getLanguage();
}
