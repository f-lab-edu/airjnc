package com.airjnc.user.util.validator;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchValidator {
    public void validate(String plain, String hash) {
        boolean isMatch = BCryptHashEncrypter.isMatch(plain, hash);
        if (!isMatch) {
            throw new PasswordIsNotMatchException();
        }
    }
}
