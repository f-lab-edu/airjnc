package com.airjnc.common.exception;


import org.springframework.validation.Errors;

public class DuplicatedException extends DefaultException {

  public DuplicatedException(Errors errors) {
    super(errors);
  }
}
