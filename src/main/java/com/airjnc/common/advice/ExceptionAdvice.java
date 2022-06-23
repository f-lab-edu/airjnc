package com.airjnc.common.advice;

import com.airjnc.common.util.exception.CommonException;
import com.airjnc.user.util.exception.DuplicatedEmailException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final MessageSource messageSource;

    // Bean Validation 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Errors errors = ex.getBindingResult();
        return ErrorResponse.of(ex, errors, messageSource);
    }

    // 이메일 중복 에러
    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCommon(DuplicatedEmailException ex) {
        Errors errors = ex.getErrors();
        return ErrorResponse.of(ex, errors, messageSource);
    }

    @Getter
    private static class ErrorResponse {
        private final String exceptionType;
        private final List<String> global;
        private final Map<String, String> field;

        public ErrorResponse(Exception ex, List<String> global, Map<String, String> field) {
            this.exceptionType = ex.getClass().getSimpleName();
            this.global = global;
            this.field = field;
        }

        public static ErrorResponse of(Exception ex, Errors errors, MessageSource messageSource) {
            Map<String, String> field = new HashMap<>();
            List<String> global = new ArrayList<>();

            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError allError : allErrors) {
                String message = Arrays
                    .stream(Objects.requireNonNull(allError.getCodes()))
                    .map(code -> {
                            Locale locale = LocaleContextHolder.getLocale();
                            Object[] arguments = allError.getArguments();
                            try {
                                return messageSource.getMessage(code, arguments, locale);
                            } catch (NoSuchMessageException e) {
                                return null;
                            }
                        }
                    ).filter(Objects::nonNull)
                    .findFirst()
                    .orElse(allError.getDefaultMessage());
                if (allError instanceof FieldError) {
                    field.put(((FieldError) allError).getField(), message);
                } else {
                    global.add(message);
                }
            }
            return new ErrorResponse(ex, global, field);
        }
    }
}
