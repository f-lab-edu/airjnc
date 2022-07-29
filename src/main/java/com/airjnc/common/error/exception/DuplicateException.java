package com.airjnc.common.error.exception;

import com.airjnc.common.error.code.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String value) {
        super(value + " is duplicated", ErrorCode.DUPLICATE_VALUE);
    }
}
