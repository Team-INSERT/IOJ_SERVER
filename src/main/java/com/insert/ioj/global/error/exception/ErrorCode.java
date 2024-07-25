package com.insert.ioj.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PROCESS_EXECUTION(500, "EXECUTION-1", "명령어를 실행하는 도중 에러가 발생하였습니다."),

    FORBIDDEN_USER(403, "USER-403-1", "요청한 사용자의 권한이 존재하지 않습니다."),
    FORBIDDEN_THIRD_YEAR(403, "USER-403-2", "요청한 사용자의 3학년 권한이 존재하지 않습니다."),
    FORBIDDEN_SECOND_YEAR(403, "USER-403-3", "요청한 사용자의 2학년 권한이 존재하지 않습니다."),
    FORBIDDEN_FIRST_YEAR(403, "USER-403-4", "요청한 사용자의 1학년 권한이 존재하지 않습니다."),

    NOT_FOUND_USER(404, "USER-404-1", "요청한 사용자가 존재하지 않습니다."),
    NOT_FOUND_PROBLEM(404, "PROBLEM-404-1", "요청한 문제가 존재하지 않습니다."),
    NOT_FOUND_CONTEST(404, "CONTEST-404-1", "요청한 대회가 존재하지 않습니다."),

    INVALID_TOKEN(401, "TOKEN-401-1", "올바르지 않은 형식의 Token입니다."),
    EXPIRED_PERIOD_TOKEN(401, "TOKEN-401-2", "기한이 만료된 Token입니다."),

    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int status;
    private final String code;
    private final String message;
}
