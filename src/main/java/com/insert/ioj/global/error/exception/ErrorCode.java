package com.insert.ioj.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, 9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int status;
    private final int code;
    private final String message;
}
