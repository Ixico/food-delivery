package com.sigmamales.sigmafoodserver.exception.notfound;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ActivationTokenNotFoundException extends ApiException {

    private ActivationTokenNotFoundException(String message) {
        super(message);
    }

    public static ActivationTokenNotFoundException withId(UUID id) {
        return new ActivationTokenNotFoundException(String.format("Activation token with id %s not found.", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ACTIVATION_TOKEN_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
