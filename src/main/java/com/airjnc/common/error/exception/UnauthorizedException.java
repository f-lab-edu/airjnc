package com.airjnc.common.error.exception;

import com.airjnc.common.error.code.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String value) {
        super(value + " is unauthorized", ErrorCode.UNAUTHORIZED_EXCEPTION);
    }

    public UnauthorizedException(String value, ErrorCode errorCode) {
        super(value + "is unauthorized", errorCode);
    }

}
