package com.insert.ioj.domain.user.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;

public record InfoUserResponse(
    String nickname,
    Color color
) {
    public InfoUserResponse(User user) {
        this(
            user.getNickname(),
            user.getColor()
        );
    }
}
