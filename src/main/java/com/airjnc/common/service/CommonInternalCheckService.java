package com.airjnc.common.service;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class CommonInternalCheckService {

  public void shouldBeMatch(int actual, int expected) {
    if (actual == expected) {
      return;
    }
    /*
    1. CommonCheckService.isNotMatch
    2. CommonCheckService
     */
    Errors errors = ErrorsFactory.create("shouldBeMatch");
    errors.reject(this.getClass().getSimpleName(), new Object[]{actual, expected}, null);
    throw new DefaultException(errors);
  }
}
