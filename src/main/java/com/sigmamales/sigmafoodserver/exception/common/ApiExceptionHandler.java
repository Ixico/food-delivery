package com.sigmamales.sigmafoodserver.exception.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Problem> handleApiException(ApiException apiException) {
        log.error("Api exception occurred", apiException);
        var httpStatus = apiException.getHttpStatus();
        var problem = Problem.builder()
                .errorMessage(apiException.getMessage())
                .errorCode(apiException.getErrorCode())
                .status(httpStatus.value())
                .build();
        return new ResponseEntity<>(problem, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleUnexpectedException(Exception exception) {
        log.error("Unexpected exception occurred", exception);
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var problem = Problem.builder()
                .errorMessage(exception.getMessage())
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .status(httpStatus.value())
                .build();
        return new ResponseEntity<>(problem, httpStatus);
    }
}
