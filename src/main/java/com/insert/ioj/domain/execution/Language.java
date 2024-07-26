package com.insert.ioj.domain.execution;

import com.insert.ioj.global.constants.CommandConstants;
import com.insert.ioj.global.constants.ExtensionConstants;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.constants.FolderConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    C(FolderConstants.C_EXECUTION_FOLDER_NAME,
        FileConstants.C_FILE_NAME,
        CommandConstants.C_COMMAND_LINE,
        ExtensionConstants.C_EXTENSION),

    CPP(FolderConstants.CPP_EXECUTION_FOLDER_NAME,
        FileConstants.CPP_FILE_NAME,
        CommandConstants.CPP_COMMAND_LINE,
        ExtensionConstants.CPP_EXTENSION),

    JAVA(FolderConstants.JAVA_EXECUTION_FOLDER_NAME,
        FileConstants.JAVA_FILE_NAME,
        CommandConstants.JAVA_COMMAND_LINE,
        ExtensionConstants.JAVA_EXTENSION),

    PYTHON(FolderConstants.PYTHON_EXECUTION_FOLDER_NAME,
        FileConstants.PYTHON_FILE_NAME,
        CommandConstants.PYTHON_COMMAND_LINE,
        ExtensionConstants.PYTHON_EXTENSION);

    private final String folderName;
    private final String sourcecodeFileName;
    private final String compilationCommand;
    private final String sourcecodeExtension;
}
