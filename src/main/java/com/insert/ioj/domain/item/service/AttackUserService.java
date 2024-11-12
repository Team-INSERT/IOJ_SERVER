package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.CustomUserItemRepository;
import com.insert.ioj.domain.item.presentation.dto.req.AttackUserRequest;
import com.insert.ioj.domain.item.presentation.dto.res.AttackResponse;
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
public class AttackUserService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final EntryRepository entryRepository;
    private final CustomUserItemRepository userItemRepository;

    @Transactional
    public AttackResponse execute(AttackUserRequest request) {
        User user = userFacade.getCurrentUser();
        Entry targetUser = entryRepository.findById(request.getTargetUserId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_USER));
        Room room = roomFacade.getRoom(request.getRoomId());

        room.isActive();

        Entry attackUser = entryRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER));
        UserItem userItem = userItemRepository.findNotUseUserItem(room, user, request.getAttackItem())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_HAVE_ITEM));

        userItem.attack(targetUser.getUser());
        attackUser.useItem();

        return new AttackResponse(request.getAttackItem(), targetUser.getId(), attackUser.getId());
    }
}
