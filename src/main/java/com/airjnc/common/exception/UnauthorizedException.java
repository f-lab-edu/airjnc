package com.airjnc.common.exception;


import com.airjnc.common.util.enumerate.ErrorCode;
import com.airjnc.common.util.factory.ErrorsFactory;

public class UnauthorizedException extends DefaultException {
  public UnauthorizedException() {
    super(ErrorsFactory.createAndReject(ErrorCode.UNAUTHORIZED.name()));
  }
}
