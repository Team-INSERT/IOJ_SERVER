package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.presentation.dto.req.CreateRoomRequest;
import com.insert.ioj.domain.room.presentation.dto.res.InfoRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.ListRoomResponse;
import com.insert.ioj.domain.room.service.CreateRoomService;
import com.insert.ioj.domain.room.service.InfoRoomService;
import com.insert.ioj.domain.room.service.ListRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Room API")
@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
    private final ListRoomService listRoomService;
    private final InfoRoomService infoRoomService;
    private final CreateRoomService createRoomService;

    @Operation(summary = "방 리스트")
    @GetMapping
    public List<ListRoomResponse> listRoom() {
        return listRoomService.execute();
    }

    @Operation(summary = "방 정보")
    @GetMapping("/{roomId}")
    public InfoRoomResponse getInfoRoom(@PathVariable UUID roomId) {
        return infoRoomService.execute(roomId);
    }

    @Operation(summary = "방 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID createRoom(@RequestBody @Valid CreateRoomRequest request) {
        return createRoomService.execute(request);
    }
}
