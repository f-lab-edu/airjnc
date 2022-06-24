package com.airjnc.common.advice;

import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.user.util.exception.DuplicatedEmailException;
import org.junit.jupiter.api.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


class ExceptionAdviceTest {
    private ExceptionAdvice exceptionAdvice;
    private String errorMessage;
    private String errorCode;

    @BeforeEach
    void beforeEach() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/errors");
        messageSource.setDefaultEncoding("UTF-8");
        this.exceptionAdvice = new ExceptionAdvice(messageSource);
        this.errorMessage = "requiredTest";
        this.errorCode = "required";
    }

    @Nested
    @DisplayName("필드 에러 발생시")
    class Field {
        @Test
        @DisplayName("MethodArgumentNotValid) global의 길이는 0이여야 하며, field의 키는 에러난 필드의 이름이 있어야 하고, 값에는 properties에서 읽은 " +
            "해당 필드의 에러 메시지여야 한다.")
        void handleMethodArgumentNotValidField() throws NoSuchMethodException {
            Method method = ExceptionAdviceTest.class.getMethod("method");
            MethodParameter methodParameter = new MethodParameter(method, -1);
            fieldTestTemplate(
                bindingResult -> new MethodArgumentNotValidException(methodParameter, bindingResult),
                exception -> exceptionAdvice.handleMethodArgumentNotValid((MethodArgumentNotValidException) exception)
            );
        }

        @Test
        @DisplayName("DuplicatedEmail) global의 길이는 0이여야 하며, field의 키는 에러난 필드의 이름이 있어야 하고, 값에는 properties에서 읽은 해당 필드의 " +
            "에러 메시지여야 한다.")
        void handleDuplicatedEmailExceptionField() {
            fieldTestTemplate(
                DuplicatedEmailException::new,
                exception -> exceptionAdvice.handleDuplicatedEmailException((DuplicatedEmailException) exception)
            );
        }
    }

    @Nested
    @DisplayName("글로벌 에러 발생시")
    class Global {
        @Test
        @DisplayName("MethodArgumentNotValid) field의 size는 0이여야 하며, global은 properties에서 읽은 글로벌 에러 메시지가 담겨있어야 " +
            "한다.")
        void handleMethodArgumentNotValidGlobal() throws NoSuchMethodException {
            Method method = ExceptionAdviceTest.class.getMethod("method");
            MethodParameter methodParameter = new MethodParameter(method, -1);
            globalTestTemplate(
                bindingResult -> new MethodArgumentNotValidException(methodParameter, bindingResult),
                exception -> exceptionAdvice.handleMethodArgumentNotValid((MethodArgumentNotValidException) exception)
            );
        }

        @Test
        @DisplayName("DuplicatedEmail) field의 size는 0이여야 하며, global은 properties에서 읽은 글로벌 에러 메시지가 담겨있어야 한다.")
        void handleDuplicatedEmailExceptionGlobal() {
            globalTestTemplate(
                DuplicatedEmailException::new,
                exception -> exceptionAdvice.handleDuplicatedEmailException((DuplicatedEmailException) exception)
            );
        }
    }


    private void fieldTestTemplate(ExceptionCallback exceptionCallback, InvokeCallback invokeCallback) {
        // given
        final String fieldName = "name";
        BindException bindingResult = new BindException(new Target(), "target");
        bindingResult.rejectValue(fieldName, this.errorCode);
        Exception exception = exceptionCallback.process(bindingResult);
        // when
        ErrorResponse errorResponse = invokeCallback.process(exception);
        // then
        assertThat(errorResponse.getExceptionType()).isEqualTo(exception.getClass().getSimpleName());
        assertThat(errorResponse.getGlobal()).isEmpty();
        Map<String, String> field = errorResponse.getField();
        assertThat(field.size()).isSameAs(1);
        assertThat(field.containsKey(fieldName)).isTrue();
        assertThat(field.get(fieldName)).isEqualTo(this.errorMessage);
    }

    private void globalTestTemplate(ExceptionCallback exceptionCallback, InvokeCallback invokeCallback) {
        // given
        BindException bindingResult = new BindException(new Target(), "target");
        bindingResult.reject(this.errorCode);
        Exception exception = exceptionCallback.process(bindingResult);
        // when
        ErrorResponse errorResponse = invokeCallback.process(exception);
        //then
        assertThat(errorResponse.getExceptionType()).isEqualTo(exception.getClass().getSimpleName());
        assertThat(errorResponse.getGlobal().size()).isSameAs(1);
        assertThat(errorResponse.getGlobal().get(0)).isEqualTo(this.errorMessage);
    }

    public void method() {
    }

    private static class Target {
        String name;

        public String getName() {
            return name;
        }
    }

    @FunctionalInterface
    private interface ExceptionCallback {
        Exception process(BindingResult bindingResult);
    }

    @FunctionalInterface
    interface InvokeCallback {
        ErrorResponse process(Exception exception);
    }
}