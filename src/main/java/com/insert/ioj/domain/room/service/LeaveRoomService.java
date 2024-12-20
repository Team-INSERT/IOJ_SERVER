package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.LeaveRoomResponse;
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
public class LeaveRoomService {
    private final RoomFacade roomFacade;
    private final UserFacade userFacade;
    private final EntryRepository entryRepository;

    @Transactional
    public LeaveRoomResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        User user = userFacade.getCurrentUser();
        Entry entryUser = entryRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER));

        if (entryUser.getHost()) {
            throw new IojException(ErrorCode.NOT_LEAVE_HOST);
        }

        room.removePeople();
        entryRepository.delete(entryUser);
        return new LeaveRoomResponse(user.getNickname());
    }
}
