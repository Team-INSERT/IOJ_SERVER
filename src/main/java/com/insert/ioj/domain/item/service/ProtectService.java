package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.CustomUserItemRepository;
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
    private final CustomUserItemRepository customUserItemRepository;

    @Transactional
    public Boolean execute(ProtectRequest request) {
        Room room = roomFacade.getRoom(request.getRoomId());
        User user = userFacade.getCurrentUser();

        customUserItemRepository.findNotUseUserItem(room, user, Item.SHIELD)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_HAVE_ITEM))
            .useShield();

        Boolean isProtect = userItemRepository.findById(request.getAttackItemId())
            .map(UserItem::protect)
            .orElse(false);

        entryRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER))
            .successProtect(isProtect);

        return isProtect;
    }
}
