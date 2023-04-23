package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class TooManyProductsException extends ApiException {

    public static TooManyProductsException instance() {
        return new TooManyProductsException();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.TOO_MANY_PRODUCTS;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
