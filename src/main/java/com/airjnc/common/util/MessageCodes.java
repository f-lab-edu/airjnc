package com.airjnc.common.util;

public enum MessageCodes {

    // HTTP
    BAD_REQUEST("BadRequest"), DUPLICATED("Duplicated"), NOT_FOUND("NotFound"), UNAUTHORIZED("Unauthorized");

    private final String code;

    MessageCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
