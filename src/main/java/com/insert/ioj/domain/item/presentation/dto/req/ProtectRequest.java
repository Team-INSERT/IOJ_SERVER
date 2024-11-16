package com.insert.ioj.domain.item.presentation.dto.req;

import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProtectRequest {
    private UUID roomId;
    private Item item;
    private Long attackItemId;
}
