package com.insert.ioj.domain.item.presentation.dto.req;

import com.insert.ioj.domain.item.domain.type.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProtectRequest {
    @NotNull(message = "roomId가 비어있습니다.")
    private UUID roomId;

    @NotNull(message = "item이 비어있습니다.")
    private Item item;

    @NotNull(message = "attackItemId이 비어있습니다.")
    private Long attackItemId;
}
