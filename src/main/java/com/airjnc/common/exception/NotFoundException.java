package com.airjnc.common.exception;


import com.airjnc.common.util.constant.MessageCodes;

public class NotFoundException extends RuntimeException {
    private static final String CODE = MessageCodes.NOT_FOUND.getCode();

    public NotFoundException(String message) {
        super(CODE + "." + message);
    }

    public NotFoundException() {
        super(CODE);
    }
}
