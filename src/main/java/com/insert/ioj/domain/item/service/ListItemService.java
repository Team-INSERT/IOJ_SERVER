package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.domain.item.presentation.dto.res.ListItemResponse;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ListItemService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final UserItemRepository userItemRepository;

    @Transactional(readOnly = true)
    public List<ListItemResponse> execute(UUID roomId) {
        User user = userFacade.getCurrentUser();
        Room room = roomFacade.getRoom(roomId);

        return userItemRepository.findByUserAndRoomAndUsedIsFalse(user, room).stream()
            .map(ListItemResponse::new)
            .toList();
    }
}
