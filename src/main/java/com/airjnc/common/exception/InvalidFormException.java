package com.airjnc.common.exception;

import com.airjnc.common.exception.code.ErrorCode;
import org.springframework.validation.Errors;

public class InvalidFormException extends CustomException {
    
    private final Errors errors;
    
    public InvalidFormException(Errors errors) {
        super(ErrorCode.INVALID_FORM);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
