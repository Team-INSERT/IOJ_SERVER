package com.insert.ioj.domain.auth.presentation.dto.res;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;
}
