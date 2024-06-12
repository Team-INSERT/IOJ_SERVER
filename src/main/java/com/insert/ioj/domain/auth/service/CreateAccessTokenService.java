package com.insert.ioj.domain.auth.service;

import com.insert.ioj.domain.auth.domain.RefreshToken;
import com.insert.ioj.domain.auth.domain.repository.RefreshTokenRepository;
import com.insert.ioj.domain.auth.presentation.dto.res.AccessTokenResponse;
import com.insert.ioj.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateAccessTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public AccessTokenResponse execute(String token) {
        RefreshToken refreshToken = getRefreshToken(token);
        return new AccessTokenResponse(jwtTokenProvider
                .createAccessToken(refreshToken.getEmail()));
    }

    private RefreshToken getRefreshToken(String token) {
        return refreshTokenRepository.findById(token)
                .orElseThrow(IllegalArgumentException::new);
    }
}
