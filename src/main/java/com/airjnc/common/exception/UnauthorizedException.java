package com.airjnc.common.exception;


import com.airjnc.common.util.MessageCodes;

public class UnauthorizedException extends RuntimeException {
    private static final String CODE = MessageCodes.UNAUTHORIZED.getCode();

    public UnauthorizedException(String message) {
        super(CODE + "." + message);
    }

    public UnauthorizedException() {
        super(CODE);
    }
}
