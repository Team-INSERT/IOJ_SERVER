package com.insert.ioj.domain.auth.presentation;

import com.insert.ioj.domain.auth.presentation.dto.req.AccessTokenRequest;
import com.insert.ioj.domain.auth.presentation.dto.res.TokenResponse;
import com.insert.ioj.domain.auth.service.GoogleAuthLinkService;
import com.insert.ioj.domain.auth.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final GoogleAuthLinkService googleAuthLinkService;
    private final GoogleAuthService googleAuthService;

    @GetMapping
    public String getGoogleAuthLink() {
        return googleAuthLinkService.execute();
    }

    @PostMapping
    public TokenResponse login(@RequestBody AccessTokenRequest accessTokenRequest) {
        return googleAuthService.execute(accessTokenRequest.getAccessToken());
    }
}
