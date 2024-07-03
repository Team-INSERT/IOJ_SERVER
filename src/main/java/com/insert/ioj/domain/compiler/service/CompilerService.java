package com.insert.ioj.domain.compiler.service;

import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import com.insert.ioj.domain.compiler.presentation.dto.res.CompileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        buildImage(id);
        return new CompileResponse(id, runCode(id));
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

    private int buildImage(String imageName) throws IOException, InterruptedException {
        String[] dockerCommand = new String[] {"docker", "image", "build", "util", "-t", imageName};
        ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);
        Process process = processBuilder.start();
        return process.waitFor();
    }

    private String runCode(String imageName) throws InterruptedException, IOException {
        String[] dockerCommand = new String[] {"docker", "run", "--rm", imageName};
        ProcessBuilder processbuilder = new ProcessBuilder(dockerCommand);
        Process process = processbuilder.start();
        process.waitFor();

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return outputReader.readLine();
    }
}
