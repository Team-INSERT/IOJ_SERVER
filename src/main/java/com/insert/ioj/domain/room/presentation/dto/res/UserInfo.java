package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfo {
    private String nickname;
    private Color color;
    private Boolean ready;
    private Boolean host;

    public UserInfo(Entry entry) {
        User user = entry.getUser();
        this.nickname = user.getNickname();
        this.color = user.getColor();
        this.ready = entry.getReady();
        this.host = entry.getHost();
    }
}
