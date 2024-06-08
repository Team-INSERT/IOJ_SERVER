package com.insert.ioj.domain.user.presentation;

import com.insert.ioj.domain.user.presentation.dto.res.InfoUserResponse;
import com.insert.ioj.domain.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final ProfileService profileService;

    @GetMapping
    public InfoUserResponse findMyInfo(String email) {
        return profileService.execute(email);
    }
}
