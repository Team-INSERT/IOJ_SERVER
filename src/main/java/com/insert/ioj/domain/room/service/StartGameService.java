package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.StartGameResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StartGameService {
    private final RoomFacade roomFacade;
    private final UserFacade userFacade;

    @Transactional
    public StartGameResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        User user = userFacade.getCurrentUser();

        room.checkHost(user);
        room.updateStatus();

        return new StartGameResponse();
    }
}
