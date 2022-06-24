package com.airjnc.common.dto;

import com.airjnc.common.advice.ExceptionAdvice;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

public class ErrorResponse {
    private final String exceptionType;
    private final List<String> global;
    private final Map<String, String> field;

    public ErrorResponse(Exception ex, List<String> global, Map<String, String> field) {
        this.exceptionType = ex.getClass().getSimpleName();
        this.global = global;
        this.field = field;
    }

    public static ErrorResponse of(Exception ex, Errors errors, MessageSource messageSource) {
        List<String> global = new ArrayList<>();
        Map<String, String> field = new HashMap<>();

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