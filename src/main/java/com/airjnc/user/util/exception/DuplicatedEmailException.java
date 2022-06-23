package com.airjnc.user.util.exception;

import com.airjnc.common.util.exception.CommonException;
import org.springframework.validation.Errors;

public class DuplicatedEmailException extends CommonException {
    public DuplicatedEmailException(Errors errors) {
        super(errors);
    }

    public DuplicatedEmailException(String message, Errors errors) {
        super(message, errors);
    }

    public DuplicatedEmailException(String message, Throwable cause, Errors errors) {
        super(message, cause, errors);
    }

    public DuplicatedEmailException(Throwable cause, Errors errors) {
        super(cause, errors);
    }
}
