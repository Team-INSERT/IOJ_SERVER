package com.insert.ioj.domain.item.presentation.dto.res;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListItemResponse {
    private Item item;

    public ListItemResponse(UserItem userItem) {
        this.item = userItem.getItem();
    }
}
