package com.airjnc.common.util.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class CommonException extends RuntimeException {
    private final Errors errors;

    public CommonException(Errors errors) {
        this.errors = errors;
    }

    public CommonException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public CommonException(String message, Throwable cause, Errors errors) {
        super(message, cause);
        this.errors = errors;
    }

    public CommonException(Throwable cause, Errors errors) {
        super(cause);
        this.errors = errors;
    }
}
