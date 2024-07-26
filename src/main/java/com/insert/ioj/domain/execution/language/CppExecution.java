package com.insert.ioj.domain.execution.language;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.Execution;
import lombok.Getter;

import java.util.List;

@Getter
public class CppExecution extends Execution {
    public CppExecution(String sourcecode,
                        List<Testcase> testcases,
                        int timeLimit,
                        int memoryLimit) {
        super(sourcecode, testcases, timeLimit, memoryLimit);
    }

    @Override
    public Language getLanguage() {
        return Language.CPP;
    }
}
