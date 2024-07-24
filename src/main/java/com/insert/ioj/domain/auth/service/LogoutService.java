package com.insert.ioj.domain.auth.service;

import com.insert.ioj.domain.auth.domain.repository.RefreshTokenRepository;
import com.insert.ioj.domain.auth.presentation.dto.req.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogoutService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void execute(RefreshTokenRequest request) {
        refreshTokenRepository.deleteById(request.getRefreshToken());
    }
}
