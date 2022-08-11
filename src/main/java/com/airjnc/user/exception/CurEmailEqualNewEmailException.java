package com.airjnc.user.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class CurEmailEqualNewEmailException extends BadRequestException {

  public CurEmailEqualNewEmailException(Errors errors) {
    super(errors);
  }
}
