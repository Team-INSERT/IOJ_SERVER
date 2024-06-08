package com.insert.ioj.domain.auth.presentation;

import com.insert.ioj.domain.auth.service.GoogleAuthLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final GoogleAuthLinkService googleAuthLinkService;

    @GetMapping
    public String getGoogleAuthLink() {
        return googleAuthLinkService.execute();
    }
}
