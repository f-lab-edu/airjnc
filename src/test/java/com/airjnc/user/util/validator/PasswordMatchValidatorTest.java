package com.airjnc.user.util.validator;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTest
class PasswordMatchValidatorTest {
    PasswordMatchValidator passwordMatchValidator;


    @BeforeEach
    void beforeEach() {
        passwordMatchValidator = new PasswordMatchValidator();
    }

    @Test
    void whenPasswordIsNotMatchThenThrowPasswordIsNotMatchException() {
        //given
        String plain = "1234";
        String hash = BCryptHashEncrypter.encrypt(plain);
        //when
        passwordMatchValidator.validate(plain, hash);
        //then
    }

    @Test
    void whenPasswordIsMatchThenWillDoNothing() {
        //given
        String plain = "123";
        String hash = BCryptHashEncrypter.encrypt("12345");
        //when
        assertThrows(PasswordIsNotMatchException.class, () -> passwordMatchValidator.validate(plain, hash));
    }
}
