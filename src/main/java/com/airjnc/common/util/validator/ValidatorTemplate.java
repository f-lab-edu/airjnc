package com.airjnc.common.util.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

@Component
public class ValidatorTemplate {
    public void validate(Validator validator, Object target) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target,
            target.getClass().getSimpleName());
        validator.validate(target, bindingResult);
    }
}
