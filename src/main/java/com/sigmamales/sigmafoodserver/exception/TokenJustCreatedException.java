package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenJustCreatedException extends ApiException {

    public static TokenJustCreatedException instance() {
        return new TokenJustCreatedException();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.TOKEN_JUST_CREATED;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
