package com.sigmamales.sigmafoodserver.exception.common;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }

    public abstract ErrorCode getErrorCode();

    public abstract HttpStatus getHttpStatus();

}
