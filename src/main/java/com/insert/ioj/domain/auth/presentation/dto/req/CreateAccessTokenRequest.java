package com.insert.ioj.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAccessTokenRequest {
    @NotNull(message = "refreshToken은 null일 수 없습니다.")
    private String refreshToken;
}
