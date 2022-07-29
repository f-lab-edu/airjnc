package com.airjnc.user.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class PasswordIsNotMatchException extends BadRequestException {

  public PasswordIsNotMatchException(Errors errors) {
    super(errors);
  }
}
