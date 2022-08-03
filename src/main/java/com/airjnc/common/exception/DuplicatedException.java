package com.airjnc.common.exception;


import com.airjnc.common.util.enumerate.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Errors;

public class DuplicatedException extends DefaultException {

  public DuplicatedException() {
    super(ErrorsFactory.create(ErrorCode.DUPLICATED));
  }

  public DuplicatedException(Errors errors) {
    super(errors);
  }

  public DuplicatedException(Errors errors, Throwable cause) {
    super(errors, cause);
  }
}
