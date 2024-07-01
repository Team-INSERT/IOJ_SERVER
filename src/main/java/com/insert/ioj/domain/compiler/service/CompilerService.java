package com.insert.ioj.domain.compiler.service;

import com.insert.ioj.domain.compiler.presentation.dto.req.CompileCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class CompilerService {
    public void execute(CompileCodeRequest request) throws IOException {
        createStartFile(
            request.getInput(),
            request.getTimeLimit(),
            request.getMemoryLimit()
        );

        saveUploadFile(request.getInput());
    }

    private void createStartFile(String input, int timeLimit, int memoryLimit) {
        String executionCommand = """
            #!/usr/bin/env bash
            ulimit -s %d
            timeout --signal=SIGTERM %d python3 main.py < %s
            exit $?
            """.formatted(memoryLimit, timeLimit, input);
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
}
