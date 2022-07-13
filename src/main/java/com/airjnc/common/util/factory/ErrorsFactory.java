package com.airjnc.common.util.factory;

import com.airjnc.common.util.constant.ErrorCode;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public final class ErrorsFactory {
    public static Errors create(Object target) {
        return new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
    }

    public static Errors create(String objectName) {
        return new BeanPropertyBindingResult(null, objectName);
    }

    public static Errors create(ErrorCode errorCode) {
        /*
        globalError
            1. "errorCode.getCode()"."objectName"
            2. "errorCode.getCode()"
        Ex) "objectName -> RootException.class.getSimpleName(), errorCode -> ErrorCode.DEFAULT"
            1. Default.RootException
            2. Default
        Ex) "objectName -> "", errorCode -> ErrorCode.DEFAULT"
            1. Default
         */
        Errors errors = new BeanPropertyBindingResult(null, "");
        errors.reject(errorCode.getCode());
        return errors;
    }
}
