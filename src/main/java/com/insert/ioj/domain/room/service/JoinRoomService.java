package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
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
    private final RoomFacade roomFacade;
    private final UserFacade userFacade;
    private final EntryRepository entryRepository;

    @Transactional
    public JoinRoomResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        User user = userFacade.getCurrentUser();

        room.addPeople(user);
        alreadyUser(user, room);
        entryRepository.save(new Entry(room, user));

        return new JoinRoomResponse(user);
    }

    private void alreadyUser(User user, Room room) {
        Boolean isUser = entryRepository.existsByUserAndRoom(user, room);
        if (isUser) {
            throw new IojException(ErrorCode.ALREADY_USER);
        }
    }
}
