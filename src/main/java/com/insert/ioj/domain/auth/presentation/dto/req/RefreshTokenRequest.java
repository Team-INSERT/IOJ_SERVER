package com.insert.ioj.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotNull(message = "refreshToken이 비어있습니다.")
    private String refreshToken;
}
