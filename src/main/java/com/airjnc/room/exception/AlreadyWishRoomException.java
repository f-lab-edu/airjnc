package com.airjnc.room.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class AlreadyWishRoomException extends BadRequestException {

  public AlreadyWishRoomException(Errors errors) {
    super(errors);
  }
}
