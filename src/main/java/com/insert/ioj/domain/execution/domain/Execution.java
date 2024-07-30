package com.insert.ioj.domain.execution.domain;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.file.FileUtil;
import lombok.Getter;

import java.io.IOException;
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

    public void createExecutionDirectory() throws IOException {
        FileUtil.createDirectory(path);
        saveUploadedFiles();
        copyDockerFile();
        copySpecialFile();
    }

    public void createEntrypointFiles() {
        testcases.forEach(testcase -> {
            String testcaseId = testcase.getId().toString();
            String inputFileName = testcaseId + "-" + FileConstants.INPUT_FILE_NAME;
            createEntrypointFile(inputFileName, testcaseId);
        });
    }

    private void saveUploadedFiles() throws IOException {
        String sourceCodeFileName = getLanguage().getSourcecodeFileName();
        FileUtil.saveUploadedFiles(sourcecode, path + "/" + sourceCodeFileName);

        for (Testcase testcase : testcases) {
            FileUtil.saveUploadedFiles(
                testcase.getInput(),
                path + "/" + testcase.getId() + "-" + FileConstants.INPUT_FILE_NAME);
        }
    }

    private void copyDockerFile() throws IOException {
        FileUtil.copyFile(getLanguage()
                .getFolderName()
                .concat("/" + FileConstants.DOCKER_FILE_NAME),
            path.concat("/" + FileConstants.DOCKER_FILE_NAME));
    }

    protected abstract void copySpecialFile() throws IOException;

    public String getExecutionFolderName() {
        return EXECUTION_FOLDER_PREFIX_NAME + id;
    }

    protected abstract void createEntrypointFile(String inputFileName, String testcaseId);

    public abstract Language getLanguage();
}
