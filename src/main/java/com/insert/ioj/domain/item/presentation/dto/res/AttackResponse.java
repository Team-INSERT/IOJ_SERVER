package com.insert.ioj.domain.item.presentation.dto.res;

import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttackResponse {
    private final String type = "ATTACK";
    private Item item;
    private Long targetUser;
    private Long attackItemId;

    public AttackResponse(Item item, Long targetUser, Long attackItemId) {
        this.item = item;
        this.targetUser = targetUser;
        this.attackItemId = attackItemId;
    }
}
