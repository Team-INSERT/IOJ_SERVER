package com.insert.ioj.domain.room.facade;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomFacade {
    private final RoomRepository roomRepository;

    public Room getRoom(UUID roomId) {
        return roomRepository.findById(roomId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM));
    }
}
