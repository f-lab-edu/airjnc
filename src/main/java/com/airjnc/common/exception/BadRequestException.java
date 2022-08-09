package com.airjnc.common.exception;


import org.springframework.validation.Errors;

public class BadRequestException extends DefaultException {

  public BadRequestException(Errors errors) {
    super(errors);
  }
}
