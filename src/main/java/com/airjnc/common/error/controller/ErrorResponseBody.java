package com.airjnc.common.error.controller;


import com.airjnc.common.error.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
public class ErrorResponseBody {
    
    private final String message;
    private int status;
    private final List<CustomFieldError> errors;

    
    private ErrorResponseBody(final MessageSource messageSource, final ErrorCode code, final List<CustomFieldError> errors) {
        this.message = getMessage(messageSource, code);
        this.status = code.getStatus();
        this.errors = errors;
    }
    
    private ErrorResponseBody(final MessageSource messageSource, final ErrorCode code){
        this.message = getMessage(messageSource, code);
        this.status = code.getStatus();
        this.errors = new ArrayList<>();
    }
    
    public static ErrorResponseBody of(final MessageSource messageSource, final ErrorCode code, final BindingResult bindingResult){
        return new ErrorResponseBody(messageSource, code, CustomFieldError.of(bindingResult));
    }

    public static ErrorResponseBody of(final MessageSource messageSource, final ErrorCode code){
        return new ErrorResponseBody(messageSource, code);
    }
    
    private static String getMessage(final MessageSource messageSource, final ErrorCode code){
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(code.getMessage(), null, locale);
        } catch (NoSuchMessageException e){
            return null;
        }
    }
    
    
    
    
    
    @Getter
    public static class CustomFieldError {
        private String field;
        private String value;
        private String reason;

        private CustomFieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
        
        public static List<CustomFieldError> of(final String field, final String value, final String reason) {
            List<CustomFieldError> customFieldErrors = new ArrayList<>();
            customFieldErrors.add(new CustomFieldError(field, value, reason));
            return customFieldErrors;
        }
        
        public static List<CustomFieldError> of(final BindingResult bindingResult){
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                .map(error -> new CustomFieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                .collect(Collectors.toList());
        }
    }
    
    
    
}
