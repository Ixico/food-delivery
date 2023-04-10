package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AddressNotFoundException extends ApiException {

    private AddressNotFoundException(String message) {
        super(message);
    }

    public static AddressNotFoundException withId(UUID id) {
        return new AddressNotFoundException(String.format("Address with id %s not found.", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ADDRESS_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
