package com.insert.ioj.infra.websocket.docker;

import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.infra.cmd.CmdUtil;
import com.insert.ioj.infra.docker.DockerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
public class DockerWebSocketHandler extends TextWebSocketHandler {
    private static final String TEST_CASE_ID_ENV_VARIABLE = "TEST_CASE_ID";
    private static final int TIME_LIMIT = 600;
    private static final int MEMORY_LIMIT = 1024;

    private final ConcurrentMap<WebSocketSession, Process> sessionProcessMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, String> map = parseMessage(session.getUri().getQuery());

        String sourcecode = map.get("sourcecode");
        Language language = Language.valueOf(map.get("language"));

        Execution execution = ExecutionFactory.createExecution(
            sourcecode,
            null,
            TIME_LIMIT,
            MEMORY_LIMIT,
            language
        );

        createEnvironmentAndBuild(execution);

        String[] command = new String[] {"docker","run", "--rm", "-i", "-e",
            TEST_CASE_ID_ENV_VARIABLE+"=execution", execution.getImageName()};

        Process process = new ProcessBuilder(command).start();
        sessionProcessMap.put(session, process);

        CmdUtil.readOutputAsync(process, output -> {
            try {
                session.sendMessage(new TextMessage(output));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CmdUtil.readErrorAsync(process, error -> {
            try {
                session.sendMessage(new TextMessage("ERROR:" + error));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        new Thread(() -> {
            try {
                process.waitFor();

                if (session.isOpen()) {
                    deleteEnvironment(execution);
                    DockerUtil.deleteImage(execution.getImageName());
                    session.close();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                sessionProcessMap.remove(session);
            }
        }).start();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Process process = sessionProcessMap.get(session);
        CmdUtil.writeInput(process, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Process process = sessionProcessMap.remove(session);
        if (process != null) {
            process.destroy();
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

    private Map<String, String> parseMessage(String payload) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = payload.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}
