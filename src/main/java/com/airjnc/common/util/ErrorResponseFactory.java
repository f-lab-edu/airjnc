package com.airjnc.common.util;

import com.airjnc.common.dto.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

public class ErrorResponseFactory {

    public static ErrorResponse create(Exception ex, MessageSource messageSource) {
        List<String> global = new ArrayList<>();
        Map<String, String> field = new HashMap<>();
        generateMessageByException(ex, messageSource, global, field);
        return new ErrorResponse(ex, global, field);
    }

    private static void generateMessageByException(Exception ex, MessageSource messageSource, List<String> global,
                                                   Map<String, String> field) {
        if (ex instanceof BindException) {
            generateMessage(((BindException) ex).getBindingResult(), messageSource, global, field);
        } else if (ex instanceof RuntimeException) {
            generateMessage(ex.getMessage(), messageSource, global, field);
        } else {
            global.add(ex.getMessage());
        }
    }

    private static void generateMessage(Errors errors, MessageSource messageSource, List<String> global, Map<String,
        String> field) {
        List<ObjectError> allErrors = errors.getAllErrors();
        for (ObjectError allError : allErrors) {
            String message = Arrays
                .stream(Objects.requireNonNull(allError.getCodes()))
                .map(code -> getMessage(messageSource, code, allError.getArguments()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(allError.getDefaultMessage());
            if (allError instanceof FieldError) {
                field.put(((FieldError) allError).getField(), message);
            } else {
                global.add(message);
            }
        }
    }

    private static void generateMessage(String code, MessageSource messageSource, List<String> global, Map<String,
        String> field) {
        String message = getMessage(messageSource, code, null);
        if (message != null) {
            global.add(message);
        } else {
            global.add(code);
        }
    }

    private static String getMessage(MessageSource messageSource, String code, Object[] arguments) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(code, arguments, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
