package com.airjnc.common.util.beanvalidator;

import com.airjnc.common.annotation.TwoFieldMatch;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TwoFieldMatchValidator implements ConstraintValidator<TwoFieldMatch, Object> {

  private String firstFieldName;

  private String secondFieldName;

  private String message;

  @Override
  public void initialize(TwoFieldMatch constraintAnnotation) {
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

      Object firstObj = firstMethod.invoke(value);
      Object secondObj = secondMethod.invoke(value);

      isValid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    if (!isValid) {
      context.buildConstraintViolationWithTemplate(message)
          /*
           * `addPropertNode` -> `@TwoFieldMatch` 를 사용할 경우, 필드 접근 방법을 정의한다.
           * Ex) `@TwoFieldMatch class Ex { String a; }` 일 경우, `addPropertyNode("a")` 해주면 된다.
           * Ex) `@TwoFieldMatch class Ex { Ex2 ex2; } class Ex2{ String a; }` 일 경우, `addPropertyNode("ex2").addPropertyNode("a")` 해주면 된다.
           */
          .addPropertyNode(secondFieldName)
          /*
           * 에러 메시지[message]와 node key[secondFieldName] 값을 넘겨주며, 해당 node는 errors[].field에 바인딩된다.
           * `addConstraintViolation` 를 사용할 경우, fieldError에 담긴다.
           * `addConstraintViolation` 를 사용하지 않고 `context.buildConstraintViolationWithTemplate(message);` 만 사용할 경우, global error에 담긴다.
           */
          .addConstraintViolation()
          // `addConstraintViolation` 를 통해 field error을 담았고, `disableDefaultConstraintViolation` 을 통해 global error을 제거한다.
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
