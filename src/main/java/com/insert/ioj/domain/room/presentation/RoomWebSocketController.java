package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.service.DeleteRoomService;
import com.insert.ioj.domain.room.service.JoinRoomService;
import com.insert.ioj.domain.room.service.LeaveRoomService;
import com.insert.ioj.domain.room.service.ReadyService;
import com.insert.ioj.domain.room.service.StartGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final JoinRoomService joinRoomService;
    private final LeaveRoomService leaveRoomService;
    private final ReadyService readyService;
    private final StartGameService startGameService;
    private final DeleteRoomService deleteRoomService;

    @GetMapping("/{roomId}/join")
    public void joinRoom(@PathVariable UUID roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, joinRoomService.execute(roomId));
    }

    @GetMapping("/{roomId}/leave")
    public void leaveRoom(@PathVariable UUID roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, leaveRoomService.execute(roomId));
    }

    @GetMapping("/{roomId}/ready")
    public void ready(@PathVariable UUID roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, readyService.execute(roomId));
    }

    @GetMapping("/{roomId}/start")
    public void startGame(@PathVariable UUID roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, startGameService.execute(roomId));
    }

    @GetMapping("/{roomId}/delete")
    public void deleteRoom(@PathVariable UUID roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, deleteRoomService.execute(roomId));
    }
}
