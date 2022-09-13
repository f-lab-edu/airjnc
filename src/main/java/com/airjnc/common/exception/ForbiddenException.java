package com.airjnc.common.exception;

import com.airjnc.common.util.enumerate.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;

public class ForbiddenException extends DefaultException {

  public ForbiddenException() {
    super(ErrorsFactory.createAndReject(ErrorCode.FORBIDDEN.name()));
  }
}
