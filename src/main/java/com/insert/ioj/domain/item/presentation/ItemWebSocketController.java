package com.insert.ioj.domain.item.presentation;

import com.insert.ioj.domain.item.presentation.dto.req.AttackUserRequest;
import com.insert.ioj.domain.item.service.AttackUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/item")
@RestController
public class ItemWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final AttackUserService attackUserService;

    @GetMapping("/attack")
    public void joinRoom(@RequestBody AttackUserRequest request) {
        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), attackUserService.execute(request));
    }
}
