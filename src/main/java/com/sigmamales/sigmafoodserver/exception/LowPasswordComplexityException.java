package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class LowPasswordComplexityException extends ApiException {

    private LowPasswordComplexityException(String message) {
        super(message);
    }

    public static LowPasswordComplexityException withReason(String reason) {
        return new LowPasswordComplexityException(reason);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_PASSWORD_EXCEPTION;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
