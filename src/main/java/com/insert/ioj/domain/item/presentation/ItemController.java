package com.insert.ioj.domain.item.presentation;

import com.insert.ioj.domain.item.presentation.dto.req.ProtectRequest;
import com.insert.ioj.domain.item.presentation.dto.res.ListAttackUsersResponse;
import com.insert.ioj.domain.item.presentation.dto.res.ListItemResponse;
import com.insert.ioj.domain.item.service.ListAttackUsersService;
import com.insert.ioj.domain.item.service.ListItemService;
import com.insert.ioj.domain.item.service.ProtectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Item API")
@RequiredArgsConstructor
@RequestMapping("/item")
@RestController
public class ItemController {
    private final ListItemService listItemService;
    private final ListAttackUsersService listAttackUsersService;
    private final ProtectService protectService;

    @Operation(summary = "가지고있는 아이템 리스트 반환")
    @GetMapping("/{roomId}")
    public List<ListItemResponse> listItem(@PathVariable UUID roomId) {
        return listItemService.execute(roomId);
    }

    @Operation(summary = "공격할 수 있는 유저 반환")
    @GetMapping("/users/{roomId}")
    public List<ListAttackUsersResponse> listAttackUsers(@PathVariable UUID roomId) {
        return listAttackUsersService.execute(roomId);
    }

    @Operation(summary = "공격받은 아이템 방어")
    @GetMapping("/protect/{roomId}")
    public Boolean protectItem(@PathVariable UUID roomId,
                                                     @RequestBody @Valid ProtectRequest request) {
        return protectService.execute(roomId, request);
    }
}
