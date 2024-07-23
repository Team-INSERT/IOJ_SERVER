package com.insert.ioj.domain.auth.presentation;

import com.insert.ioj.domain.auth.presentation.dto.req.AccessTokenRequest;
import com.insert.ioj.domain.auth.presentation.dto.req.RefreshTokenRequest;
import com.insert.ioj.domain.auth.presentation.dto.res.AccessTokenResponse;
import com.insert.ioj.domain.auth.presentation.dto.res.TokenResponse;
import com.insert.ioj.domain.auth.service.CreateAccessTokenService;
import com.insert.ioj.domain.auth.service.GoogleAuthLinkService;
import com.insert.ioj.domain.auth.service.GoogleAuthService;
import com.insert.ioj.domain.auth.service.LogoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API")
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final GoogleAuthLinkService googleAuthLinkService;
    private final GoogleAuthService googleAuthService;
    private final CreateAccessTokenService createNewAccessToken;
    private final LogoutService logoutService;

    @Operation(summary = "구글 로그인 링크 조히")
    @GetMapping
    public String getGoogleAuthLink() {
        return googleAuthLinkService.execute();
    }

    @Operation(summary = "유저 인증을 위한 토큰 발급")
    @PostMapping
    public TokenResponse login(@RequestBody @Valid AccessTokenRequest accessTokenRequest) {
        return googleAuthService.execute(accessTokenRequest.getAccessToken());
    }

    @Operation(summary = "accessToken 재발급")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/refresh")
    public AccessTokenResponse createNewAccessToken(@RequestBody @Valid RefreshTokenRequest request) {
        return createNewAccessToken.execute(request.getRefreshToken());
    }

    @Operation(summary = "로그아웃")
    @DeleteMapping
    public void logout(@RequestBody @Valid RefreshTokenRequest request) {
        logoutService.execute(request);
    }
}
