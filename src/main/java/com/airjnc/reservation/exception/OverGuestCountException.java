package com.airjnc.reservation.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class OverGuestCountException extends BadRequestException {

  public OverGuestCountException(Errors errors) {
    super(errors);
  }
}
