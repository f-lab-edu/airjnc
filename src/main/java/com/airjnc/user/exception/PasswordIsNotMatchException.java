package com.airjnc.user.exception;

import com.airjnc.common.exception.BadRequestException;

public class PasswordIsNotMatchException extends BadRequestException {
    public PasswordIsNotMatchException() {
        super("PasswordIsNotMatch");
    }
}
