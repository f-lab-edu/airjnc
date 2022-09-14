package com.airjnc.reservation.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class ReservationBetweenException extends BadRequestException {

  public ReservationBetweenException(Errors errors) {
    super(errors);
  }
}
