package com.airjnc.common.advice;

import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.exception.DuplicatedException;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.util.ErrorResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final MessageSource messageSource;

    // Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error("[INTERNAL_SERVER_ERROR]", ex);
        return ErrorResponseFactory.create(ex, messageSource);
    }

    // Bean Validation Error [400]
    @ExceptionHandler({MethodArgumentNotValidException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(Exception ex) {
        return ErrorResponseFactory.create(ex, messageSource);
    }

    // UNAUTHORIZED ERROR [401]
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        return ErrorResponseFactory.create(ex, messageSource);
    }

    // 404 NOT FOUND
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return ErrorResponseFactory.create(ex, messageSource);
    }


    // CONFLECT ERROR [409]
    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedEmailException(DuplicatedException ex) {
        return ErrorResponseFactory.create(ex, messageSource);
    }
}
