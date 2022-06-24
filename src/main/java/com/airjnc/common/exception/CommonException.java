package com.airjnc.common.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class CommonException extends RuntimeException {
    private final Errors errors;

    public CommonException(Errors errors) {
        this.errors = errors;
    }
}
