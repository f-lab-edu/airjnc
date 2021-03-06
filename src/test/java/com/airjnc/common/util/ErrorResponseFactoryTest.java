package com.airjnc.common.util;

import static org.assertj.core.api.Assertions.assertThat;
import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.common.util.factory.ErrorResponseFactory;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

@UnitTest
class ErrorResponseFactoryTest {

  ResourceBundleMessageSource messageSource;

  @BeforeEach
  void beforeEach() {
    messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages/errors"); // message 기본 경로 설정
    messageSource.setDefaultEncoding("UTF-8");
  }

  @Test
  void whenCodeIsInPropertiesThenGetTheMessageInProperties() {
    //given
    DefaultException ex = new DefaultException();
    //when
    ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
    //then
    assertThat(errorResponse.getGlobal().size()).isSameAs(1);
    assertThat(errorResponse.getGlobal().get(0)).isEqualTo("message from errors.properties");
    assertThat(errorResponse.getField().size()).isSameAs(0);
  }

  @Test
  void whenPassBindExceptionThenSuccessfullyResolveMessage() {
    //given
    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Target(), "target");
    bindingResult.rejectValue("test", "required");
    BindException ex = new BindException(bindingResult);
    //when
    ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
    //then
    assertThat(errorResponse.getGlobal().size()).isSameAs(0);
    assertThat(errorResponse.getField().size()).isSameAs(1);
    assertThat(errorResponse.getField().get("test")).isEqualTo("required message");
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
    ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
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

  // argument 학습 테스트
  @Test
  void argumentsTest() {
    //given
    CommonInternalCheckService commonInternalCheckService = new CommonInternalCheckService();
    ErrorResponse errorResponse = null;
    int actual = 1;
    int expected = 2;
    //when
    try {
      commonInternalCheckService.shouldBeMatch(actual, expected);
    } catch (DefaultException ex) {
      errorResponse = ErrorResponseFactory.create(ex, messageSource);
    }
    assertThat(errorResponse.getGlobal()).isNotNull();
    assertThat(errorResponse.getGlobal().get(0))
        .isEqualTo(String.format("actual: %d, but expected: %d", actual, expected));
  }


  private static class Target {

    private String test;

    private String field;

    public String getTest() {
      return test;
    }

    public String getField() {
      return field;
    }
  }
}
