package com.airjnc.reservation.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class ReservationIsNotMineException extends BadRequestException {

  public ReservationIsNotMineException(Errors errors) {
    super(errors);
  }
}
