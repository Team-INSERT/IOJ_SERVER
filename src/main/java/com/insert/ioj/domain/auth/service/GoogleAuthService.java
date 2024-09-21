package com.insert.ioj.domain.auth.service;

import com.insert.ioj.domain.auth.presentation.dto.res.TokenResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.repository.UserRepository;
import com.insert.ioj.domain.user.domain.type.Authority;
import com.insert.ioj.domain.user.domain.type.Color;
import com.insert.ioj.global.feign.auth.GoogleInformationClient;
import com.insert.ioj.global.feign.auth.res.GoogleInformationResponse;
import com.insert.ioj.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    private final GoogleInformationClient googleInformationClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public TokenResponse execute(String accessToken) {
        GoogleInformationResponse response = googleInformationClient
                .getUserInformation(accessToken);
        String email = response.getEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            userRepository.save(
                new User(email, response.getName(), ramdomColor(), getAuthority(email)));
        }

        return new TokenResponse(
            jwtTokenProvider.createAccessToken(email),
            jwtTokenProvider.createRefreshToken(email)
        );
    }

    private Color ramdomColor() {
        Random random = new Random();
        Color[] colors = Color.values();
        return colors[random.nextInt(colors.length)];
    }

    private Authority getAuthority(String email) {
        if (email.endsWith("@bssm.hs.kr")) {
            if (email.startsWith("2022")) {
                return Authority.THIRD_YEAR;
            } else if (email.startsWith("2023")) {
                return Authority.SECOND_YEAR;
            } else if (email.startsWith("24.")) {
                return Authority.FIRST_YEAR;
            } else {
                return Authority.USER;
            }
        } else {
            return Authority.USER;
        }
    }
}
