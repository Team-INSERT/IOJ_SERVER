package com.insert.ioj.global.feign;

import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 401) {
            return new IojException(ErrorCode.INVALID_TOKEN);
        }
        return new IojException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
