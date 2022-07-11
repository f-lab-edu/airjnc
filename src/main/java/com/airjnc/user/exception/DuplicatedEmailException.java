package com.airjnc.user.exception;

import com.airjnc.common.exception.DuplicatedException;

public class DuplicatedEmailException extends DuplicatedException {
    public DuplicatedEmailException() {
        super("Email");
    }
}
