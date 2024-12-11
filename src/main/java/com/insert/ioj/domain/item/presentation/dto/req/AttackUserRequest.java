package com.insert.ioj.domain.item.presentation.dto.req;

import com.insert.ioj.domain.item.domain.type.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class AttackUserRequest {
    @NotNull(message = "roomId가 비어있습니다.")
    private UUID roomId;

    @NotNull(message = "targetUserId가 비어있습니다.")
    private Long targetUserId;

    @NotNull(message = "attackItemId가 비어있습니다.")
    private Item attackItem;
}
