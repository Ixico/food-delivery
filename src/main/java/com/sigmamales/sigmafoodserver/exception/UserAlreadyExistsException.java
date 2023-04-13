package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {

    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException withEmail(String email) {
        return new UserAlreadyExistsException(String.format("User with email %s already exists.", email));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_ALREADY_EXISTS;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
