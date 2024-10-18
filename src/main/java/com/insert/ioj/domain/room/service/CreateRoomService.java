package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
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
    private final EntryRepository entryRepository;

    @Transactional
    public UUID execute(CreateRoomRequest request) {
        User host = userFacade.getCurrentUser();
        Room room = request.toEntity(host);

        entryRepository.save(new Entry(room, host, true));
        return roomRepository.save(room).getId();
    }
}
