package com.airjnc.common.error.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "ErrorCode.INVALID_INPUT_VALUE"),
    DUPLICATE_VALUE(400, "ErrorCode.DUPLICATE_VALUE"),
    BUSINESS_EXCEPTION(400, "ErrorCode.BUSINESS_EXCEPTION"),
    ENTITY_NOT_FOUND_EXCEPTION(400, "ErrorCode.ENTITY_NOT_FOUND_EXCEPTION"),
    UNAUTHORIZED_EXCEPTION(401, "ErrorCode.UNAUTHORIZED_EXCEPTION"),
    USER_LOGIN_NOT_MATCH_EXCEPTION(401, "ErrorCode.USER_LOGIN_NOT_MATCH_EXCEPTION");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}

