package com.insert.ioj.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenRequest {
    @NotNull(message = "accessToken은 null일 수 없습니다.")
    private String accessToken;
}
