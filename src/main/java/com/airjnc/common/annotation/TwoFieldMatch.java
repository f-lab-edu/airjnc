package com.airjnc.common.annotation;

import com.airjnc.common.util.beanvalidator.TwoFieldMatchValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

// DTO 바인딩 검사에서 선택된 두 필드에 대하여 해당 두 필드의 값이 동일한 지 체크하는 애노테이션입니다.
// 향후 사용할 가능성이 있을 것 같아, 현재 사용하진 않지만 지우지 않고 남겨둡니다.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TwoFieldMatchValidator.class)
public @interface TwoFieldMatch {

  String first();

  Class<?>[] groups() default {};

  String message() default "{TwoFieldMatch}";

  Class<? extends Payload>[] payload() default {};

  String second();

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    TwoFieldMatch[] value();
  }
}
