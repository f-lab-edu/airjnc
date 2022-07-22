package com.airjnc.user.exception;

import com.airjnc.common.error.code.ErrorCode;
import com.airjnc.common.error.exception.UnauthorizedException;

public class UserLoginNotMatchException extends UnauthorizedException {

    public UserLoginNotMatchException() {
        super("Check ID and Password", ErrorCode.USER_LOGIN_NOT_MATCH_EXCEPTION);
    }
}
