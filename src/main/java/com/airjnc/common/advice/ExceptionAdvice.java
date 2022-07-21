package com.airjnc.common.advice;

import com.airjnc.common.dto.ErrorResponse;
import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.exception.DuplicatedException;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.util.factory.ErrorResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

  private final MessageSource messageSource;

  // 500 - Server Error
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception ex) {
    log.error("[INTERNAL_SERVER_ERROR]", ex);
    return ErrorResponseFactory.create(ex);
  }

  // 400 - Bean Validation
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValid(BindException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 400 - BAD REQUEST
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleBadRequestException(BadRequestException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }


  // 401 - UNAUTHORIZED
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 404 - NOT FOUND
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFoundException(NotFoundException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }


  // 409 - CONFLICT
  @ExceptionHandler(DuplicatedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleDuplicatedEmailException(DuplicatedException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }
}
