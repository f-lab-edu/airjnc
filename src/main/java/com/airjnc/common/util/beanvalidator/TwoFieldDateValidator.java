package com.airjnc.common.util.beanvalidator;

import com.airjnc.common.annotation.TwoFieldDate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TwoFieldDateValidator implements ConstraintValidator<TwoFieldDate, Object> {

  private String firstFieldName;

  private String secondFieldName;

  private String message;

  @Override
  public void initialize(TwoFieldDate constraintAnnotation) {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
    message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    boolean isValid = true;
    try {
      Method firstMethod = value.getClass().getMethod("get" + replaceFirstToUpperCase(firstFieldName));
      Method secondMethod = value.getClass().getMethod("get" + replaceFirstToUpperCase(secondFieldName));

      LocalDate firstObj = (LocalDate) firstMethod.invoke(value);
      LocalDate secondObj = (LocalDate) secondMethod.invoke(value);

      isValid = firstObj.isBefore(secondObj);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    if (!isValid) {
      context.buildConstraintViolationWithTemplate(message)
          .addPropertyNode(secondFieldName)
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
    }
    return isValid;
  }

  private String replaceFirstToUpperCase(String str) {
    char c = Character.toUpperCase(str.charAt(0));
    StringBuilder sb = new StringBuilder();
    int length = str.length();
    sb.append(c);
    for (int i = 1; i < length; i++) {
      sb.append(str.charAt(i));
    }
    return sb.toString();
  }
}
