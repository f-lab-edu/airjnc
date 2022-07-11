package com.airjnc.common.exception;


import com.airjnc.common.util.constant.MessageCodes;

public class BadRequestException extends RuntimeException {
    private static final String CODE = MessageCodes.BAD_REQUEST.getCode();

    public BadRequestException(String message) {
        super(CODE + "." + message);
    }

    public BadRequestException() {
        super(CODE);
    }
}
