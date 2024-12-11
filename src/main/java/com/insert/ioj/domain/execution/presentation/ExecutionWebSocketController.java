package com.insert.ioj.domain.execution.presentation;

import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.execution.presentation.dto.req.ExecutionRequest;
import com.insert.ioj.domain.execution.presentation.dto.req.InputRequest;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.cmd.CmdUtil;
import com.insert.ioj.infra.docker.DockerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
@Controller
public class ExecutionWebSocketController {
    private static final String TEST_CASE_ID_ENV_VARIABLE = "TEST_CASE_ID";
    private static final int TIME_LIMIT = 600;
    private static final int MEMORY_LIMIT = 1024;

    private final ConcurrentMap<String, Process> sessionProcessMap = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/execution")
    public void handleExecutionRequest(@Valid ExecutionRequest request) {
        String sessionId = request.getSessionId();
        Language language = Language.valueOf(request.getLanguage());

        Execution execution = ExecutionFactory.createExecution(
            request.getSourcecode(),
            null,
            TIME_LIMIT,
            MEMORY_LIMIT,
            language
        );

        createEnvironmentAndBuild(execution);

        String[] command = new String[] {"docker","run", "--rm", "-i", "-e",
            TEST_CASE_ID_ENV_VARIABLE+"=execution", execution.getImageName()};

        Process process;
        try {
            process = new ProcessBuilder(command).start();
            sessionProcessMap.put(sessionId, process);

            CmdUtil.readOutputAsync(process, output -> {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                messagingTemplate.convertAndSend("/topic/output/" + sessionId, output);
            });

            CmdUtil.readErrorAsync(process, error -> {
                messagingTemplate.convertAndSend("/topic/error/" + sessionId, error);
            });

            new Thread(() -> {
                try {
                    int status = process.waitFor();
                    Thread.sleep(5);
                    messagingTemplate.convertAndSend("/topic/output/" + sessionId,
                        "Process finished with exit code " + status);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    deleteEnvironment(execution);
                    DockerUtil.deleteImage(execution.getImageName());
                    sessionProcessMap.remove(sessionId);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/input")
    public void handleUserInput(@Valid InputRequest request) {
        Process process = sessionProcessMap.get(request.getSessionId());
        if (process != null) {
            try {
                CmdUtil.writeInput(process, request.getInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createEnvironmentAndBuild(Execution execution) {
        buildExecutionEnvironment(execution);
        execution.createEntrypointFiles();

        String dockerfilePath = execution.getPath() + "/" + FileConstants.DOCKER_FILE_NAME;
        DockerUtil.buildImage(dockerfilePath, execution.getImageName(), execution.getPath());
    }

    private void buildExecutionEnvironment(Execution execution) {
        try {
            execution.createExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEnvironment(Execution execution) {
        try {
            execution.deleteExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
