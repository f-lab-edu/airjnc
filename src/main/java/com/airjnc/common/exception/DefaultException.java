package com.airjnc.common.exception;

import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Errors;

public class DefaultException extends RuntimeException {

  private final Errors errors;

  public DefaultException() {
    this.errors = ErrorsFactory.create(ErrorCode.DEFAULT);
  }

  public DefaultException(Errors errors) {
    this.errors = errors;
  }

  public DefaultException(Errors errors, Throwable cause) {
    super(cause);
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }
}
