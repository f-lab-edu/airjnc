package com.airjnc.user.exception;

import com.airjnc.common.error.code.ErrorCode;
import com.airjnc.common.error.exception.EntityNotFoundException;

public class FindEmailNotMatchException extends EntityNotFoundException {
    
    public FindEmailNotMatchException() {
        super("Check Name and PhoneNumber", ErrorCode.FIND_EMAIL_NOT_MATCH_EXCEPTION);
    }
    
}
