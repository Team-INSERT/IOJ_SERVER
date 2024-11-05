package com.insert.ioj.domain.item.presentation.dto.res;

import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GiveItemResponse {
    private Long targetUser;
    private Item item;

    public GiveItemResponse(Long targetUser, Item item) {
        this.targetUser = targetUser;
        this.item = item;
    }
}
