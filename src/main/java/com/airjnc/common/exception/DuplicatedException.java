package com.airjnc.common.exception;


import com.airjnc.common.util.MessageCodes;

public class DuplicatedException extends RuntimeException {
    private static final String CODE = MessageCodes.DUPLICATED.getCode();

    public DuplicatedException(String message) {
        super(CODE + "." + message);
    }

    public DuplicatedException() {
        super(CODE);
    }
}
