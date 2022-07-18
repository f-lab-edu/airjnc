package com.airjnc.common.error.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    
    INVALID_INPUT_VALUE(400, "ErrorCode.INVALID_INPUT_VALUE"),
    DUPLICATE_VALUE(400, "ErrorCode.DUPLICATE_VALUE");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}

