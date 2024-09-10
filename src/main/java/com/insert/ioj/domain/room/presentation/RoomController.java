package com.insert.ioj.domain.room.presentation;

import com.insert.ioj.domain.room.presentation.dto.req.CreateRoomRequest;
import com.insert.ioj.domain.room.service.CreateRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Room API")
@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
    private final CreateRoomService createRoomService;

    @Operation(summary = "방 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID createRoom(@RequestBody @Valid CreateRoomRequest request) {
        return createRoomService.execute(request);
    }
}
