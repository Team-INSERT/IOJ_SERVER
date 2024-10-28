package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.item.presentation.dto.req.ProtectRequest;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProtectService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final EntryRepository entryRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public Boolean execute(ProtectRequest request) {
        Room room = roomFacade.getRoom(request.getRoomId());
        User user = userFacade.getCurrentUser();
        User attackUser = entryRepository.findById(request.getAttackUser())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_TARGET)).getUser();

        userItemRepository.findFirstByUserAndItemAndUsedIsFalse(user, Item.SHIELD)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_HAVE_ITEM))
            .useShield();

        userItemRepository.findFirstByItemAndUserAndTargetUserAndRoom(
            request.getItem(), attackUser, user, room)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ITEM))
            .protect();

        return true;
    }
}
