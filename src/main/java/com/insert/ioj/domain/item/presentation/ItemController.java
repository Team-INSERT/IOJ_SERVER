package com.insert.ioj.domain.item.presentation;

import com.insert.ioj.domain.item.presentation.dto.res.ListItemResponse;
import com.insert.ioj.domain.item.service.ListItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "가지고있는 아이템 리스트 반환")
    @GetMapping("/{roomId}")
    public List<ListItemResponse> listItem(@PathVariable UUID roomId) {
        return listItemService.execute(roomId);
    }
}
