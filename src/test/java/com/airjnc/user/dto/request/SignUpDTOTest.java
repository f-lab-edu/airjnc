package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.util.fixture.SignUpDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpDTOTest {
    Validator validator;

    @BeforeEach
    void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void plainShouldBeChangedToHash() {
        //given
        String plain = "q1w2e3r4!";
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().password(plain).build();
        //when
        signUpDTO.changePasswordToHash();
        //then
        assertThat(signUpDTO.getPassword()).isNotEqualTo(plain);
        assertThat(BCryptHashEncrypter.isMatch(plain, signUpDTO.getPassword())).isTrue();
    }
}
