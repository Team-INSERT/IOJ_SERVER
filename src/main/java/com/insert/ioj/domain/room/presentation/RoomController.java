package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.presentation.dto.req.CreateRoomRequest;
import com.insert.ioj.domain.room.presentation.dto.res.ListRoomResponse;
import com.insert.ioj.domain.room.service.CreateRoomService;
import com.insert.ioj.domain.room.service.ListRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Room API")
@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
    private final ListRoomService listRoomService;
    private final CreateRoomService createRoomService;

    @Operation(summary = "방 리스트")
    @GetMapping
    public List<ListRoomResponse> listRoom() {
        return listRoomService.execute();
    }

    @Operation(summary = "방 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID createRoom(@RequestBody @Valid CreateRoomRequest request) {
        return createRoomService.execute(request);
    }
}