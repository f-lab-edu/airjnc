package com.airjnc.user.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class WrongPasswordCodeException extends BadRequestException {

  public WrongPasswordCodeException(Errors errors) {
    super(errors);
  }
}
