package com.airjnc.reservation.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class NowIsAfterStartDateException extends BadRequestException {

  public NowIsAfterStartDateException(Errors errors) {
    super(errors);
  }
}
