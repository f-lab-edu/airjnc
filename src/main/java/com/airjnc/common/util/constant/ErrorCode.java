package com.airjnc.common.util.constant;

public enum ErrorCode {

    DEFAULT("Default"),
    BAD_REQUEST("BadRequest"),
    DUPLICATED("Duplicated"),
    NOT_FOUND("NotFound"),
    UNAUTHORIZED("Unauthorized");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
