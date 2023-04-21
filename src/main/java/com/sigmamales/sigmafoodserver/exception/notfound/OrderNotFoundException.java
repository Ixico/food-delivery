package com.sigmamales.sigmafoodserver.exception.notfound;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class OrderNotFoundException extends ApiException {

    private OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException withId(UUID id) {
        return new OrderNotFoundException(String.format("Order with id %s not found", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
