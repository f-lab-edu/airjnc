package com.airjnc.common.aop;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.auth.service.AuthService;
import com.airjnc.common.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@RequiredArgsConstructor
public class AuthCheckAspect {

    private final AuthService authService;

    @Before("@annotation(com.airjnc.common.annotation.UserLoginCheck)")
    public void userLoginCheck(JoinPoint jp) throws Throwable {
        log.debug("AOP - User Login Check Start");

        AuthInfoDTO authInfo = authService.getAuthInfo();

        if (authInfo == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
    }
}
