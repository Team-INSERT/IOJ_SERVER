package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.DeleteRoomResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DeleteRoomService {
    private final RoomRepository roomRepository;
    private final EntryRepository entryRepository;
    private final RoomFacade roomFacade;
    private final UserFacade userFacade;

    @Transactional
    public DeleteRoomResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        User user = userFacade.getCurrentUser();
        List<Entry> entryList = entryRepository.findAllByRoom(room);

        room.checkHost(user);

        entryRepository.deleteAll(entryList);
        roomRepository.delete(room);
        return new DeleteRoomResponse();
    }
}
