package com.airjnc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {

    Role role() default Role.NOT_LOGIN;

    enum Role {
        NOT_LOGIN, // 로그인 필요없음
        LOGIN      // 로그인 필요
    }
}
