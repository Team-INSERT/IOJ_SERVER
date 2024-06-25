package com.insert.ioj.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private static final String errorLogsFormat = """
        \n{
            "status": "%s",
            "code": "%s",
            "message": "%s"
        }
        """;

    private int status;
    private String code;
    private String message;

    public String toString() {
        return errorLogsFormat.formatted(
            status,
            code,
            message
        );
    }
}
