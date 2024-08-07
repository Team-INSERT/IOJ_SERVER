package com.insert.ioj.domain.compiler.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.CompileResponse;
import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.docker.DockerUtil;
import com.insert.ioj.infra.file.FileUtil;
import com.insert.ioj.infra.status.StatusUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CompilerService {
    public CompileResponse execute(CompileCodeRequest request) throws Exception {
        String id = UUID.randomUUID().toString();
        String inputFileName = id + "_input";

        createStartFile(
            inputFileName,
            request.getTimeLimit(),
            request.getMemoryLimit()
        );

        FileUtil.saveUploadedFiles(request.getInput(), "util/"+inputFileName);
        FileUtil.saveUploadedFiles(request.getSourcecode(), "util/main.py");

        DockerUtil.buildImage("util/Dockerfile", id, "util");
        return runCode(id);
    }

    public ProblemCompileResponse execute(Problem problem, List<Testcase> testcases, String sourcecode) throws Exception {
        for(Testcase testcase: testcases) {
            String id = testcase.getId().toString();
            String inputFileName = id + "_input";

            createStartFile(
                inputFileName,
                problem.getTimeLimit(),
                problem.getMemoryLimit()
            );

            FileUtil.saveUploadedFiles(testcase.getInput(), inputFileName);
            FileUtil.saveUploadedFiles(sourcecode, "util/main.py");

            DockerUtil.buildImage("util/Dockerfile", id, "util");
            CompileResponse compile = runCode(id);
            FileUtil.deleteFile("util/"+inputFileName);

            if (!testcase.getOutput().equals(compile.getResult())) {
                Verdict verdict = StatusUtil.statusResponse(compile.getStatus(), false);
                return new ProblemCompileResponse(problem.getId(), compile.getMessage(), false, verdict);
            }
        }

        Verdict verdict = StatusUtil.statusResponse(0, true);
        return new ProblemCompileResponse(problem.getId(), "Success", true, verdict);
    }

    private void createStartFile(String inputFileName, int timeLimit, int memoryLimit) throws IOException {
        String executionCommand = """
            #!/usr/bin/env bash
            ulimit -s %d
            timeout --signal=SIGTERM %d python3 main.py < %s
            exit $?
            """.formatted(memoryLimit, timeLimit, inputFileName);
        FileUtil.saveUploadedFiles(executionCommand, "util/start.sh");
    }

    private CompileResponse runCode(String id) {
        ProcessOutput processOutput = DockerUtil.runContainer(id);
        String statusResponse = checkStatus(processOutput.getStatus());

        return CompileResponse.builder()
            .id(id)
            .status(processOutput.getStatus())
            .message(statusResponse)
            .result(processOutput.getStdOut())
            .build();
    }

    private String checkStatus(int status) {
        String response;
        if (status == 0)
            response = "Success";
        else if (status == 1)
            response = "Runtime Error";
        else if (status == 2)
            response = "Compilation Error";
        else if (status == 139)
            response = "Out Of Memory";
        else
            response = "Time Limit Exceeded";
        return response;
    }
}
