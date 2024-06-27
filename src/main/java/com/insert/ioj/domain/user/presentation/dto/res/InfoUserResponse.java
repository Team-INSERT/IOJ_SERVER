package com.insert.ioj.domain.user.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InfoUserResponse {
    private String nickname;
    private Color color;

    public InfoUserResponse(User user) {
        this.nickname = user.getNickname();
        this.color = user.getColor();
    }
}
