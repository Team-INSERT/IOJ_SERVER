package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.domain.room.presentation.dto.res.JoinRoomResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JoinRoomService {
    private final RoomRepository roomRepository;
    private final UserFacade userFacade;
    private final EntryRepository entryRepository;

    @Transactional
    public JoinRoomResponse execute(UUID roomId) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM));
        User user = userFacade.getCurrentUser();

        entryRepository.save(new Entry(room, user));

        return new JoinRoomResponse(user);
    }
}
