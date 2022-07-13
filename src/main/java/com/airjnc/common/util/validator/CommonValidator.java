package com.airjnc.common.util.validator;

import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CommonValidator {
    public void validateEqual(int actual, int expected) {
        if (actual != expected) {
            Errors errors = ErrorsFactory.create("validateEqual");
            errors.reject(ErrorCode.BAD_REQUEST.getCode(), new Object[]{actual, expected}, null);
            throw new BadRequestException(errors);
        }
    }
}
