package com.airjnc.user.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class UserIsNotDeletedException extends BadRequestException {

  public UserIsNotDeletedException(Errors errors) {
    super(errors);
  }
}
