package com.insert.ioj.domain.auth.service;

import com.insert.ioj.global.config.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GoogleAuthLinkService {
    private static final String QUERY_STRING = "?client_id=%s&redirect_uri=%s" +
            "&response_type=token&scope=https://www.googleapis.com/auth/userinfo.email";
    private final AuthProperties authProperties;

    public String execute() {
        return authProperties.getGoogleBaseUrl() +
                String.format(
                        QUERY_STRING,
                        authProperties.getGoogleClientId(),
                        authProperties.getGoogleRedirectUrl()
                );
    }
}
