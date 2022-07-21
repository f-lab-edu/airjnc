package com.airjnc.user.exception;

import com.airjnc.common.exception.DuplicatedException;
import org.springframework.validation.Errors;

public class DuplicatedEmailException extends DuplicatedException {

  public DuplicatedEmailException(Errors errors) {
    super(errors);
  }
}
