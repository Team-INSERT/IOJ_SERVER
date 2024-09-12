package com.insert.ioj.domain.room.presentation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReadyResponse {
    private final String type = "READY";
    private String nickname;
    private Boolean ready;

    public ReadyResponse(String nickname, Boolean ready) {
        this.nickname = nickname;
        this.ready = ready;
    }
}
