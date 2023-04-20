package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class OldPasswordNotMatchException extends ApiException {

    public static OldPasswordNotMatchException instance() {
        return new OldPasswordNotMatchException();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.OLD_PASSWORD_NOT_MATCH;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
