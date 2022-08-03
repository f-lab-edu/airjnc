package com.airjnc.common.exception;


import com.airjnc.common.util.enumerate.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.validation.Errors;

public class BadRequestException extends DefaultException {

  public BadRequestException() {
    super(ErrorsFactory.create(ErrorCode.BAD_REQUEST));
  }

  public BadRequestException(Errors errors) {
    super(errors);
  }

  public BadRequestException(Errors errors, Throwable cause) {
    super(errors, cause);
  }
}
