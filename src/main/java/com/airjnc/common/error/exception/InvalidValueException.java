package com.airjnc.common.error.exception;

import com.airjnc.common.error.code.ErrorCode;

public class InvalidValueException extends BusinessException {
    
    public InvalidValueException(String value) {
        super(value + " is not valid", ErrorCode.INVALID_INPUT_VALUE);
    }
    
    public InvalidValueException(String value, ErrorCode errorCode){
        super(value + " is not valid", errorCode);
    }
    
    
}
