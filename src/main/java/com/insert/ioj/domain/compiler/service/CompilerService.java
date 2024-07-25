package com.insert.ioj.domain.compiler.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.CompileResponse;
import com.insert.ioj.domain.compiler.presentation.dto.res.ProblemCompileResponse;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.infra.cmd.CmdUtil;
import com.insert.ioj.infra.docker.DockerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        saveUploadFile(request.getInput(), inputFileName);
        saveUploadFile(request.getSourcecode());

        DockerUtil.buildImage(id);
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

            saveUploadFile(testcase.getInput(), inputFileName);
            saveUploadFile(sourcecode);

            DockerUtil.buildImage(id);
            CompileResponse compile = runCode(id);
            deleteFile(inputFileName);

            if (!testcase.getOutput().equals(compile.getResult())) {
                return new ProblemCompileResponse(problem.getId(), compile.getStatus(), false);
            }
        }

        return new ProblemCompileResponse(problem.getId(), "Success", true);
    }

    private void createStartFile(String inputFileName, int timeLimit, int memoryLimit) {
        String executionCommand = """
            #!/usr/bin/env bash
            ulimit -s %d
            timeout --signal=SIGTERM %d python3 main.py < %s
            exit $?
            """.formatted(memoryLimit, timeLimit, inputFileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream("util/start.sh");
            os.write(executionCommand.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveUploadFile(String content) throws IOException {
        byte[] bytes = content.getBytes();
        Path path = Paths.get("util/main.py");
        Files.write(path, bytes);
    }

    private void saveUploadFile(String content, String fileName) throws IOException {
        byte[] bytes = content.getBytes();
        Path path = Paths.get("util/" + fileName);
        Files.write(path, bytes);
    }

    private CompileResponse runCode(String id) throws InterruptedException, IOException {
        String[] dockerCommand = new String[] {"docker", "run", "--rm", id};
        ProcessBuilder processbuilder = new ProcessBuilder(dockerCommand);
        Process process = processbuilder.start();
        int status = process.waitFor();
        String statusResponse = checkStatus(status);

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String output = CmdUtil.readOutput(outputReader);
        return CompileResponse.builder()
            .id(id)
            .status(statusResponse)
            .result(output)
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

    private void deleteFile(String filename) {
        File file = new File("util/"+filename);
        file.delete();
    }
}
