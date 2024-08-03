package com.insert.ioj.domain.execution.domain;

import com.insert.ioj.domain.Testcase.domain.Testcase;

import java.util.List;

public interface AbstractExecutionFactory {
    Execution createExecution(String sourcecode,
                              List<Testcase> testcases,
                              int timeLimit,
                              int memoryLimit);
}
