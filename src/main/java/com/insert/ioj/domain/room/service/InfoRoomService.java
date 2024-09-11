package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.domain.room.presentation.dto.res.InfoRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.UserInfo;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InfoRoomService {
    private final RoomRepository roomRepository;
    private final EntryRepository entryRepository;

    public InfoRoomResponse execute(UUID roomId) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM));

        List<UserInfo> users = entryRepository.findAllByRoom(room)
            .stream().map(UserInfo::new)
            .collect(Collectors.toList());
        users.add(new UserInfo(room.getHost()));

        return new InfoRoomResponse(room, users);
    }
}
