package com.airjnc.user.exception;

import com.airjnc.common.exception.DuplicatedException;
import org.springframework.validation.Errors;

public class EmailIsDuplicatedException extends DuplicatedException {

  public EmailIsDuplicatedException(Errors errors) {
    super(errors);
  }
}
