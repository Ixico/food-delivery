package com.sigmamales.sigmafoodserver.exception.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Problem {

    private Integer status;

    private ErrorCode errorCode;

    private String errorMessage;

}
