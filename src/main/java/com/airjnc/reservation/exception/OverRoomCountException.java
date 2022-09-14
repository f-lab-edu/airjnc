package com.airjnc.reservation.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class OverRoomCountException extends BadRequestException {


  public OverRoomCountException(Errors errors) {
    super(errors);
  }
}
