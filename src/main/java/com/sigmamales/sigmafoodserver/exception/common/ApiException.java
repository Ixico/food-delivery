package com.sigmamales.sigmafoodserver.exception.common;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    public abstract ErrorCode getErrorCode();

    public abstract HttpStatus getHttpStatus();

}
