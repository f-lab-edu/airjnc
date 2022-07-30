package com.airjnc.common.util;

import static org.assertj.core.api.Assertions.assertThat;
import com.airjnc.common.dto.response.ExceptionResponse;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.util.factory.ErrorResponseFactory;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;

@UnitTest
class ExceptionResponseFactoryTest {

  ResourceBundleMessageSource messageSource;

  // argument 학습 테스트
  @Test
  void argumentsTest() {
    //given
    CommonCheckService commonCheckService = new CommonCheckService();
    ExceptionResponse exceptionResponse = null;
    int actual = 1;
    int expected = 2;
    //when
    try {
      commonCheckService.shouldBeMatch(actual, expected);
    } catch (DefaultException ex) {
      exceptionResponse = ErrorResponseFactory.create(ex, messageSource);
    }
    assertThat(exceptionResponse.getGlobal()).isNotNull();
    assertThat(exceptionResponse.getGlobal().get(0))
        .isEqualTo(String.format("%d != %d", actual, expected));
  }

  @BeforeEach
  void beforeEach() {
    messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages/errors"); // message 기본 경로 설정
    messageSource.setDefaultEncoding("UTF-8");
  }

  // 어떤 Code들이 나오는지 학습 테스트
  @Test
  void codesTest() {
    //given
    Target target = new Target();
    Errors errors = ErrorsFactory.create(target);
    errors.rejectValue("field", "errorCode");
//    errors.reject(ErrorCode.DUPLICATED.name());
    EmailIsDuplicatedException ex = new EmailIsDuplicatedException(errors);
    //when
    ExceptionResponse exceptionResponse = ErrorResponseFactory.create(ex, messageSource);
    //then
    /*
     * objectName = "Target"
     * field -> "field"
     * errorCode -> "errorCode"
     *
     * FieldError [errorCode.objectName.field]
     *   1. errorCode.Target.field
     *   2. errorCode.field
     *   3. errorCode.java.lang.String
     *   4. errorCode
     *
     * GlobalError [errorCode.objectName]
     *   1. errorCode.Target
     *   2. errorCode
     */
  }

  @Test
  void whenDefaultExceptionThenSuccessfullyResolveMessage() {
    //given
    DefaultException ex = new DefaultException();
    //when
    ExceptionResponse exceptionResponse = ErrorResponseFactory.create(ex, messageSource);
    //then
    assertThat(exceptionResponse.getGlobal().size()).isSameAs(1);
    assertThat(exceptionResponse.getGlobal().get(0)).isEqualTo("Error");
    assertThat(exceptionResponse.getField().size()).isSameAs(0);
  }

  private static class Target {

    private String test;

    private String field;

    public String getField() {
      return field;
    }

    public String getTest() {
      return test;
    }
  }
}
