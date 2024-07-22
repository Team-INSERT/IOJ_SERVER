package com.insert.ioj.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenRequest {
    @NotNull(message = "accessToken이 비어있습니다.")
    private String accessToken;
}
