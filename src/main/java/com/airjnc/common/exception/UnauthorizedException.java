package com.airjnc.common.exception;


import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Errors;

public class UnauthorizedException extends DefaultException {
    public UnauthorizedException() {
        super(ErrorsFactory.create(ErrorCode.UNAUTHORIZED));
    }

    public UnauthorizedException(Errors errors) {
        super(errors);
    }

    public UnauthorizedException(Errors errors, Throwable cause) {
        super(errors, cause);
    }
}
