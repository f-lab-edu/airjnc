package com.airjnc.common.util;

import com.airjnc.common.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;

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
        RuntimeException ex = new RuntimeException("code");
        //when
        ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
        //then
        assertThat(errorResponse.getGlobal().size()).isSameAs(1);
        assertThat(errorResponse.getGlobal().get(0)).isEqualTo("message from errors.properties");
        assertThat(errorResponse.getField().size()).isSameAs(0);
    }

    @Test
    void whenCodeIsNotInPropertiesThenGetTheCode() {
        // given
        RuntimeException ex = new RuntimeException("code1");
        //when
        ErrorResponse errorResponse = ErrorResponseFactory.create(ex, messageSource);
        //then
        assertThat(errorResponse.getGlobal().size()).isSameAs(1);
        assertThat(errorResponse.getGlobal().get(0)).isEqualTo("code1");
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
}
