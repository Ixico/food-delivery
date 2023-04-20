package com.sigmamales.sigmafoodserver.exception;

import com.sigmamales.sigmafoodserver.exception.common.ApiException;
import com.sigmamales.sigmafoodserver.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExportingOrdersException extends ApiException {

    private ExportingOrdersException(String message) {
        super(message);
    }

    public static ExportingOrdersException withReason(String reason) {
        return new ExportingOrdersException(reason);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.COULD_NOT_EXPORT_ORDERS;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
