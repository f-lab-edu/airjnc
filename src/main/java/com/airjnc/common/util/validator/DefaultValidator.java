package com.airjnc.common.util.validator;

import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Validator;

public interface DefaultValidator extends Validator {

  default void validate(Object target) {
    this.validate(target, ErrorsFactory.create(target));
  }
}
