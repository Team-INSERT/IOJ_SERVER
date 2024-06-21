package com.insert.ioj.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(404, "USER-404-1", "요청한 사용자가 존재하지 않습니다."),

    INVALID_TOKEN(403, "TOKEN-403-1", "올바르지 않은 형식의 Token입니다."),
    EXPIRED_PERIOD_TOKEN(403, "TOKEN-403-2", "기한이 만료된 Token입니다."),

    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int status;
    private final String code;
    private final String message;
}
