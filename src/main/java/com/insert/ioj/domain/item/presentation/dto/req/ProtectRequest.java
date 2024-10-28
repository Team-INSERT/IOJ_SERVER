package com.insert.ioj.domain.item.presentation.dto.req;

import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProtectRequest {
    private Item item;
    private Long attackUser;
}
