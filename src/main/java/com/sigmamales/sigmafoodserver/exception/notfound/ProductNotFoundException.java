package com.sigmamales.sigmafoodserver.exception.notfound;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ProductNotFoundException extends ApiException {

    private ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException withId(UUID id) {
        return new ProductNotFoundException(String.format("Product with id %s not found.", id));
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PRODUCT_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
