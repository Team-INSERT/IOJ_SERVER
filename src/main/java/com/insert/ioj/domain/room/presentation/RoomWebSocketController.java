package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.presentation.dto.res.DeleteRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.JoinRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.LeaveRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.ReadyResponse;
import com.insert.ioj.domain.room.service.DeleteRoomService;
import com.insert.ioj.domain.room.service.JoinRoomService;
import com.insert.ioj.domain.room.service.LeaveRoomService;
import com.insert.ioj.domain.room.service.ReadyService;
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
    private final LeaveRoomService leaveRoomService;
    private final ReadyService readyService;
    private final DeleteRoomService deleteRoomService;

    @GetMapping("/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public JoinRoomResponse joinRoom(@PathVariable UUID roomId) {
        return joinRoomService.execute(roomId);
    }

    @GetMapping("/{roomId}/leave")
    @SendTo("/topic/room/{roomId}")
    public LeaveRoomResponse leaveRoom(@PathVariable UUID roomId) {
        return leaveRoomService.execute(roomId);
    }

    @GetMapping("/{roomId}/ready")
    @SendTo("/topic/room/{roomId}")
    public ReadyResponse ready(@PathVariable UUID roomId) {
        return readyService.execute(roomId);
    }

    @GetMapping("/{roomId}/delete")
    @SendTo("/topic/room/{roomId}")
    public DeleteRoomResponse deleteRoom(@PathVariable UUID roomId) {
        return deleteRoomService.execute(roomId);
    }
}
