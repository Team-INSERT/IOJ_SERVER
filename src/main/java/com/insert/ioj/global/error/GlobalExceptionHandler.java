package com.insert.ioj.global.error;

import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IojException.class)
    public ResponseEntity<ErrorResponse> handleIojException(IojException e) {
        final ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
            .status(errorCode.getStatus())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();
        log.error(response.toString());
        return new ResponseEntity<>(
            response,
            HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleBadRequestExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Map<String, String>> getErrorsMap(Map<String, String> errors) {
        Map<String, Map<String, String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
