package com.insert.ioj.global.config;

import com.insert.ioj.domain.execution.domain.AbstractExecutionFactory;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.language.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LanguageConfig {
    public LanguageConfig() {
        configureLanguages();
    }

    private void configureLanguages() {
        register(Language.C, CExecution::new);
        register(Language.CPP, CppExecution::new);
        register(Language.JAVA, JavaExecution::new);
        register(Language.PYTHON, PythonExecution::new);
    }

    private void register(Language language, AbstractExecutionFactory factory) {
        ExecutionFactory.registerExecution(language, factory);
    }
}
