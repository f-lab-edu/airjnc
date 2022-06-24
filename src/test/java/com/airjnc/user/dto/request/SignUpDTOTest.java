package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.util.fixture.SignUpDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SignUpDTOTest {
    Validator validator;

    @BeforeEach
    void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("@Email 에러 검증")
    void wrongEmail() {
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().email("123").build();
        //when
        Set<ConstraintViolation<SignUpDTO>> violationSet = this.validator.validate(signUpDTO);
        //then
        assertThat(violationSet.size()).isSameAs(1);
        for (ConstraintViolation<SignUpDTO> violation : violationSet) {
            System.out.println(violation.getPropertyPath().toString());
            assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
            assertThat(violation.getMessageTemplate()).contains("Email");
        }
    }

    @Test
    @DisplayName("Pattern 에러 검증")
    void wrongPassword() {
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().password("123").build();
        //when
        Set<ConstraintViolation<SignUpDTO>> violationSet = this.validator.validate(signUpDTO);
        //then
        assertThat(violationSet.size()).isSameAs(1);
        for (ConstraintViolation<SignUpDTO> violation : violationSet) {
            System.out.println(violation.getPropertyPath().toString());
            assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
            assertThat(violation.getMessageTemplate()).contains("Pattern");
        }
    }

    @Test
    @DisplayName("@NotNull 에러 검증")
    void wrongData() {
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().email(null).build();
        //when
        Set<ConstraintViolation<SignUpDTO>> violationSet = this.validator.validate(signUpDTO);
        //then
        assertThat(violationSet.size()).isSameAs(1);
        for (ConstraintViolation<SignUpDTO> violation : violationSet) {
            System.out.println(violation.getPropertyPath().toString());
            assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
            assertThat(violation.getMessageTemplate()).contains("NotNull");
        }
    }

    @Test
    @DisplayName("SignUpDTO.changePasswordToHash를 호출하면, 평문으로 저장되어 있는 비밀번호가 해시로 변경되어야 한다.")
    void changePasswordToHash() {
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