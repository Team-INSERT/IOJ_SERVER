package com.insert.ioj.domain.item.presentation.dto.res;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.type.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListAttackUsersResponse {
    private Long targetId;
    private String nickname;
    private Color color;

    public ListAttackUsersResponse(Entry entry) {
        User user = entry.getUser();
        this.targetId = entry.getId();
        this.nickname = user.getNickname();
        this.color = user.getColor();
    }
}
