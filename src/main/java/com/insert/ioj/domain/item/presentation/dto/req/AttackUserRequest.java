package com.insert.ioj.domain.item.presentation.dto.req;

import com.insert.ioj.domain.item.domain.type.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class AttackUserRequest {
    private UUID roomId;
    private Long targetUserId;
    private Item attackItem;
}
