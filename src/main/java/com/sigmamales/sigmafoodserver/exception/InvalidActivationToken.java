package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidActivationToken extends ApiException {

    public static InvalidActivationToken instance() {
        return new InvalidActivationToken();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ACTIVATION_TOKEN;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
