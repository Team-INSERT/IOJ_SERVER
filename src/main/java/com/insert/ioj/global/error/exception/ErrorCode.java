package com.insert.ioj.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(404, "USER-404-1", "요청한 사용자가 존재하지 않습니다."),

    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int status;
    private final String code;
    private final String message;
}
