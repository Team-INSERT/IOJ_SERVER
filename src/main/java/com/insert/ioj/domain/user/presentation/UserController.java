package com.insert.ioj.domain.user.presentation;

import com.insert.ioj.domain.user.presentation.dto.res.InfoUserResponse;
import com.insert.ioj.domain.user.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final ProfileService profileService;

    @Operation(summary = "유저 상세 정보 조회")
    @GetMapping
    public InfoUserResponse findMyInfo(String email) {
        return profileService.execute(email);
    }
}
