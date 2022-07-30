package com.airjnc.common.annotation;

import com.airjnc.common.util.beanvalidator.TwoFieldMatchValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TwoFieldMatchValidator.class)
public @interface TwoFieldMatch {

  String message() default "{TwoFieldMatch}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String first();

  String second();

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    TwoFieldMatch[] value();
  }
}
