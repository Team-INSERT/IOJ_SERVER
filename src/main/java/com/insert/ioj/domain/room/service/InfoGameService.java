package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.problemRoom.domain.ProblemRoom;
import com.insert.ioj.domain.problemRoom.domain.repository.ProblemRoomRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.InfoGameResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InfoGameService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final EntryRepository entryRepository;
    private final ProblemRoomRepository problemRoomRepository;

    public InfoGameResponse execute(UUID roomId) {
        User user = userFacade.getCurrentUser();
        Room room = roomFacade.getRoom(roomId);

        room.isActive();
        Long userId = entryRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER)).getId();

        List<Long> problems = problemRoomRepository.findByRoom(room).stream()
            .map(ProblemRoom::getId)
            .toList();

        return new InfoGameResponse(userId, room, problems);
    }
}
