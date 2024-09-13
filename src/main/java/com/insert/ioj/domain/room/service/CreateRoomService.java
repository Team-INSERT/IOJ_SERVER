package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.domain.room.presentation.dto.req.CreateRoomRequest;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreateRoomService {
    private final UserFacade userFacade;
    private final RoomRepository roomRepository;

    @Transactional
    public UUID execute(CreateRoomRequest request) {
        User host = userFacade.getCurrentUser();
        return roomRepository.save(request.toEntity(host)).getId();
    }
}
