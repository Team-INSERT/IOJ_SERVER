package com.insert.ioj.global.error;

import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IojException.class)
    public ResponseEntity<ErrorResponse> handleIojException(IojException e) {
        final ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(
            errorCode.getStatus(),
            errorCode.getCode(),
            errorCode.getMessage()
        );
        log.error(response.toString());
        return new ResponseEntity<>(
            response,
            HttpStatus.valueOf(errorCode.getStatus())
        );
    }
}
