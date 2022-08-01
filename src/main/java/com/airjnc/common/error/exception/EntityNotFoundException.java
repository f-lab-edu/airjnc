package com.airjnc.common.error.exception;

import com.airjnc.common.error.code.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String value) {
        super(value + " is not exist", ErrorCode.ENTITY_NOT_FOUND_EXCEPTION);
    }

    public EntityNotFoundException(String value, ErrorCode errorCode) {
        super(value + "is not exist", errorCode);
    }

}
