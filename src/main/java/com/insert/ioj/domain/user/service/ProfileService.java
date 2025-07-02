package com.insert.ioj.domain.user.service;

import com.insert.ioj.domain.submission.facade.EntityFacade;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.domain.user.presentation.dto.res.InfoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final EntityFacade entityFacade;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public InfoUserResponse execute() {
        Long userId = userFacade.getCurrentUserId();
        User user = entityFacade.getUserById(userId);

        return new InfoUserResponse(user);
    }
}
