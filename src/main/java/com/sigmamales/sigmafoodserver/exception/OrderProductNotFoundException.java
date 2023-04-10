package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class OrderProductNotFoundException extends ApiException {

    private OrderProductNotFoundException(String message) {
        super(message);
    }

    public static OrderProductNotFoundException withId(UUID id) {
        return new OrderProductNotFoundException(String.format("Order product with id %s not found.", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_PRODUCT_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
