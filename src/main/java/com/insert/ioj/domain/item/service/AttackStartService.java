package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttackStartService {
    private final UserFacade userFacade;
    private final UserItemRepository userItemRepository;

    @Transactional
    public void execute(Long attackItemId) {
        User user = userFacade.getCurrentUser();
        UserItem userItem = userItemRepository.findById(attackItemId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ITEM));

        if (!userItem.getTargetUser().equals(user)) {
            throw new IojException(ErrorCode.NOT_MATCH_USER_ITEM);
        }

        userItem.attackStart();
    }
}
