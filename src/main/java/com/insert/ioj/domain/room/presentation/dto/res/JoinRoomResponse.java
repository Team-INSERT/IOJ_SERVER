package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRoomResponse {
    private final String type = "JOIN";
    private String nickname;
    private Color color;

    public JoinRoomResponse(User user) {
        this.nickname = user.getNickname();
        this.color = user.getColor();
    }
}
