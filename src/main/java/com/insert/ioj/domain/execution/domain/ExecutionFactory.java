package com.insert.ioj.domain.execution.domain;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.language.Language;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ExecutionFactory {
    public static ConcurrentHashMap<Language, AbstractExecutionFactory> executions = new ConcurrentHashMap<>();

    public static void registerExecution(Language language, AbstractExecutionFactory factory) {
        executions.put(language, factory);
    }

    public static Execution createExecution(String sourcecode,
                                            List<Testcase> testcases,
                                            int timeLimit,
                                            int memoryLimit,
                                            Language language) {
        AbstractExecutionFactory factory = executions.get(language);

        return factory.createExecution(
                sourcecode,
                testcases,
                timeLimit,
                memoryLimit
        );
    }
}
