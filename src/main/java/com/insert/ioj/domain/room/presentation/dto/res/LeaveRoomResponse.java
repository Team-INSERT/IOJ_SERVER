package com.insert.ioj.domain.room.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LeaveRoomResponse {
    private final String type = "LEAVE";
    private String nickname;

    public LeaveRoomResponse(String nickname) {
        this.nickname = nickname;
    }
}
