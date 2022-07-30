package com.airjnc.common.util.factory;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public final class ErrorsFactory {

  public static Errors create(Object target, String objectName) {
    return new BeanPropertyBindingResult(target, objectName);
  }

  public static Errors create(Object target) {
    return create(target, target.getClass().getSimpleName());
  }

  public static Errors create(String objectName) {
    return create(null, objectName);
  }

  public static Errors createAndReject(Object target, String objectName, String errorCode, Object[] errorArgs) {
    /*
     * messageCode 생성 순위
        1. errorCode.objectName
        2. errorCode
     * Example) errorCode->"DEFAULT", objectName->""
        1. DEFAULT
     */
    Errors errors = new BeanPropertyBindingResult(target, objectName);
    errors.reject(errorCode, errorArgs, null);
    return errors;
  }

  public static Errors createAndReject(String errorCode) {
    return createAndReject(null, "", errorCode, null);
  }

  public static Errors createAndReject(String objectName, String errorCode) {
    return createAndReject(null, objectName, errorCode, null);
  }

  public static Errors createAndReject(String objectName, String errorCode, Object[] errorArgs) {
    return createAndReject(null, objectName, errorCode, errorArgs);
  }
}
