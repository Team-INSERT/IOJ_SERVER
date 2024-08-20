package com.insert.ioj.infra.websocket.docker;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class DockerWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {}

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {}
}
