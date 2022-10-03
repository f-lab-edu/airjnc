package com.airjnc.room.exception;

import com.airjnc.common.exception.BadRequestException;
import org.springframework.validation.Errors;

public class NotWishRoomException extends BadRequestException {

  public NotWishRoomException(Errors errors) {
    super(errors);
  }
}
