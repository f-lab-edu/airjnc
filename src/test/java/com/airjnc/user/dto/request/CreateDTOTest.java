package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.util.fixture.CreateDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDTOTest {
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
        CreateDTO createDTO = CreateDTOFixture.getBuilder().password(plain).build();
        //when
        createDTO.changePasswordToHash();
        //then
        assertThat(createDTO.getPassword()).isNotEqualTo(plain);
        assertThat(BCryptHashEncrypter.isMatch(plain, createDTO.getPassword())).isTrue();
    }
}
