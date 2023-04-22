package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends ApiException {

    public static InvalidJwtException instance() {
        return new InvalidJwtException();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_JWT;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
