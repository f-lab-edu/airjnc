package com.airjnc.common.error.controller;

import com.airjnc.common.error.code.ErrorCode;
import com.airjnc.common.error.exception.BusinessException;
import com.airjnc.common.error.exception.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    MessageSource messageSource;

    private HttpStatus resolveHttpStatus(ErrorCode code) {
        return HttpStatus.resolve(code.getStatus());
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseBody> handleBindException(BindException e) {
        log.error(e.getMessage());
        final ErrorResponseBody errorResponseBody = ErrorResponseBody.of(messageSource, ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return ResponseEntity.status(resolveHttpStatus(ErrorCode.INVALID_INPUT_VALUE)).body(errorResponseBody);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorResponseBody> handleValidationException(ValidationException e) {
        log.error(e.getMessage());
        final ErrorResponseBody errorResponseBody = ErrorResponseBody.of(messageSource, ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(resolveHttpStatus(ErrorCode.INVALID_INPUT_VALUE)).body(errorResponseBody);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseBody> handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        final ErrorResponseBody errorResponseBody = ErrorResponseBody.of(messageSource, e.getErrorCode());
        return ResponseEntity.status(resolveHttpStatus(ErrorCode.BUSINESS_EXCEPTION)).body(errorResponseBody);
    }

    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<ErrorResponseBody> handleDuplicateException(DuplicateException e) {
        log.error(e.getMessage());
        final ErrorResponseBody errorResponseBody = ErrorResponseBody.of(messageSource, ErrorCode.DUPLICATE_VALUE);
        return ResponseEntity.status(resolveHttpStatus(ErrorCode.DUPLICATE_VALUE)).body(errorResponseBody);
    }


}
