package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.presentation.dto.res.JoinRoomResponse;
import com.insert.ioj.domain.room.service.JoinRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/room")
@Controller
public class RoomWebSocketController {
    private final JoinRoomService joinRoomService;

    @GetMapping("/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public JoinRoomResponse joinRoom(@PathVariable UUID roomId) {
        return joinRoomService.execute(roomId);
    }
}
