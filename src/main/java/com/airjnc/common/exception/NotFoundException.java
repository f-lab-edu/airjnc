package com.airjnc.common.exception;


import com.airjnc.common.util.enumerate.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;

public class NotFoundException extends DefaultException {

  public NotFoundException() {
    super(ErrorsFactory.createAndReject(ErrorCode.NOT_FOUND.name()));
  }

  public NotFoundException(String objectName, Object[] errorArgs) {
    super(ErrorsFactory.createAndReject(objectName, ErrorCode.NOT_FOUND.name(), errorArgs));
  }
}
