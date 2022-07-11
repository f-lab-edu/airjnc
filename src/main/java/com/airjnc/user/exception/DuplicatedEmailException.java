package com.airjnc.user.exception;

import com.airjnc.common.exception.CommonException;
import org.springframework.validation.Errors;

public class DuplicatedEmailException extends CommonException {
    public DuplicatedEmailException(Errors errors) {
        super(errors);
    }
}
