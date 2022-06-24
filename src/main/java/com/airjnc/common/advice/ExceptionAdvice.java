package com.airjnc.common.advice;

import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.user.util.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
    public ErrorResponse handleDuplicatedEmailException(DuplicatedEmailException ex) {
        Errors errors = ex.getErrors();
        return ErrorResponse.of(ex, errors, messageSource);
    }
}
