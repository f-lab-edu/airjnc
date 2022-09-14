package com.airjnc.common.annotation;

import com.airjnc.common.util.beanvalidator.TwoFieldDateValidator;
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
@Constraint(validatedBy = TwoFieldDateValidator.class)
public @interface TwoFieldDate {

  String first();

  Class<?>[] groups() default {};

  String message() default "{TwoFieldDate}";

  Class<? extends Payload>[] payload() default {};

  String second();

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    TwoFieldDate[] value();
  }
}
