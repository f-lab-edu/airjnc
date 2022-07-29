package com.airjnc.common.exception;


import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Errors;

public class NotFoundException extends DefaultException {

  public NotFoundException() {
    super(ErrorsFactory.create(ErrorCode.NOT_FOUND));
  }

  public NotFoundException(Errors errors) {
    super(errors);
  }

  public NotFoundException(Errors errors, Throwable cause) {
    super(errors, cause);
  }
}
