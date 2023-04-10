package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApiException {

    private UserNotFoundException(String message) {
        super(message);
    }

    private UserNotFoundException() {
        super();
    }

    public static UserNotFoundException instance() {
        return new UserNotFoundException();
    }

    public static UserNotFoundException withId(UUID id) {
        return new UserNotFoundException(String.format("User with id %s not found.", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
