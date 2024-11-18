package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttackStartService {
    private final UserItemRepository userItemRepository;

    @Transactional
    public void execute(Long attackItemId) {
        UserItem userItem = userItemRepository.findById(attackItemId)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ITEM));

        userItem.attackStart();
    }
}
