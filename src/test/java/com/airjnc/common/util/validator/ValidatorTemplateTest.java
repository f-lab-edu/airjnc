package com.airjnc.common.util.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import static org.mockito.BDDMockito.*;

class ValidatorTemplateTest {
    ValidatorTemplate validatorTemplate;

    @BeforeEach
    void beforeEach() {
        this.validatorTemplate = new ValidatorTemplate();
    }

    @Test
    void validate() {
        //given
        Validator validator = Mockito.mock(Validator.class);
        Object target = new Object();
        //when
        this.validatorTemplate.validate(validator, target);
        //then
        then(validator).should(times(1)).validate(eq(target), any(BeanPropertyBindingResult.class));
    }
}
