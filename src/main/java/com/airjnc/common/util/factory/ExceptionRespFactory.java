package com.airjnc.common.util.factory;

import com.airjnc.common.dto.response.ExceptionResp;
import com.airjnc.common.exception.DefaultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public final class ExceptionRespFactory {

  public static ExceptionResp create(Exception ex) {
    List<String> global = new ArrayList<>();
    global.add(ex.getMessage());
    return new ExceptionResp(ex, global, null);
  }

  public static ExceptionResp create(Exception ex, MessageSource messageSource) {
    List<String> global = new ArrayList<>();
    Map<String, String> field = new HashMap<>();
    List<ObjectError> allErrors = getAllErrors(ex);
    for (ObjectError allError : allErrors) {
      String message = Arrays
          .stream(Objects.requireNonNull(allError.getCodes()))
          .map(code -> getMessage(code, messageSource, allError.getArguments()))
          .filter(Objects::nonNull)
          .findFirst()
          .orElse(allError.getDefaultMessage());
      if (allError instanceof FieldError) {
        field.put(((FieldError) allError).getField(), message);
      } else {
        global.add(message);
      }
    }
    return new ExceptionResp(ex, global, field);
  }

  private static List<ObjectError> getAllErrors(Exception ex) {
    if (ex instanceof BindException) {
      return ((BindException) ex).getBindingResult().getAllErrors();
    } else {
      return ((DefaultException) ex).getErrors().getAllErrors();
    }
  }


  private static String getMessage(String code, MessageSource messageSource, Object[] arguments) {
    Locale locale = LocaleContextHolder.getLocale();
    try {
      return messageSource.getMessage(code, arguments, locale);
    } catch (NoSuchMessageException e) {
      return null;
    }
  }
}
