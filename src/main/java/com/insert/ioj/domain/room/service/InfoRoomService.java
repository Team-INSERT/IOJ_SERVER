package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.InfoRoomResponse;
import com.insert.ioj.domain.room.presentation.dto.res.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InfoRoomService {
    private final EntryRepository entryRepository;
    private final RoomFacade roomFacade;

    public InfoRoomResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        List<UserInfo> users = entryRepository.findAllByRoom(room)
            .stream().map(UserInfo::new)
            .collect(Collectors.toList());
        users.add(new UserInfo(room.getHost()));

        return new InfoRoomResponse(room, users);
    }
}
