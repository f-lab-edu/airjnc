package com.airjnc.common.exception.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_FORM(400, null, "Invalid Form");

    
    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    
}

