package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenAlreadyRevokedException extends ApiException {

    public static TokenAlreadyRevokedException instance() {
        return new TokenAlreadyRevokedException();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.TOKEN_ALREADY_REVOKED;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
