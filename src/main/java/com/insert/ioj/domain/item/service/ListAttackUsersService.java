package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.presentation.dto.res.ListAttackUsersResponse;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ListAttackUsersService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final EntryRepository entryRepository;

    @Transactional(readOnly = true)
    public List<ListAttackUsersResponse> execute(UUID roomId) {
        User user = userFacade.getCurrentUser();
        Room room = roomFacade.getRoom(roomId);

        room.isActive();
        notInUser(user, room);

        return entryRepository.findAllByRoom(room).stream()
            .filter(entry -> !entry.getUser().equals(user))
            .map(ListAttackUsersResponse::new)
            .toList();
    }

    private void notInUser(User user, Room room) {
        Boolean isUser = entryRepository.existsByUserAndRoom(user, room);
        if (!isUser) {
            throw new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER);
        }
    }
}
