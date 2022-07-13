package com.airjnc.common.util;

import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.factory.ErrorResponseFactory;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.common.util.validator.CommonValidator;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;

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


    private static class Target {
        private String test;

        public String getTest() {
            return test;
        }
    }


    // 어떤 Code들이 나오는지 학습 테스트
    @Test
    void codesTest() {
        //given
        CreateDTO createDTO = CreateDTOFixture.getBuilder().build();
        Errors errors = ErrorsFactory.create(createDTO);
        errors.rejectValue("email", ErrorCode.DUPLICATED.getCode());
        errors.reject(ErrorCode.DUPLICATED.getCode());
        DuplicatedEmailException ex = new DuplicatedEmailException(errors);
        //when
        ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
        //then
        /*
         * ErrorCode -> ErrorCode.DUPLICATED -> "Duplicated"
         * field -> "email"
         * objectName -> CreateDTO.class.getSimpleName() -> "createDTO"
         * FieldError [errorCode.objectName.field]
         *   1. Duplicated.CreateDTO.email
         *   2. Duplicated.email
         *   3. Duplicated.java.lang.String
         *   4. Duplicated
         * GlobalError [errorCode.objectName]
         *   1. Duplicated.CreateDTO
         *   2. Duplicated
         */
    }

    // argument 학습 테스트
    @Test
    void argumentsTest() {
        //given
        CommonValidator commonValidator = new CommonValidator();
        ErrorResponse errorResponse = null;
        int actual = 1;
        int expected = 2;
        //when
        try {
            commonValidator.validateEqual(actual, expected);
        } catch (BadRequestException ex) {
            errorResponse = ErrorResponseFactory.create(ex, messageSource);
        }
        assertThat(errorResponse.getGlobal()).isNotNull();
        assertThat(errorResponse.getGlobal().get(0))
            .isEqualTo(String.format("actual: %d, but expected: %d", actual, expected));
    }
}
