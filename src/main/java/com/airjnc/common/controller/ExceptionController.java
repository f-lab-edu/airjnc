package com.airjnc.common.controller;

import com.airjnc.common.dto.response.ExceptionResponse;
import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.exception.DuplicatedException;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.util.factory.ErrorResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

  private final MessageSource messageSource;

  // 400 - Bean Validation, BAD REQUEST
  @ExceptionHandler({BindException.class, BadRequestException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleBadRequestException(Exception ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 500 - Server Error
  // 이미 짐작하고 있었던 서버 에러들
  // DefaultException으로 던진 에러들
  @ExceptionHandler(DefaultException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleDefaultException(DefaultException ex) {
    log.error("[INTERNAL_SERVER_ERROR]", ex);
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 409 - CONFLICT
  @ExceptionHandler(DuplicatedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ExceptionResponse handleDuplicatedEmailException(DuplicatedException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 500 - Server Error
  // 짐작하지 못 했던 에러들
  // 내가 직접 만든 DefaultException이 아니라, "Exception(or)RuntimeException"이 던져진 에러들
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleException(Exception ex) {
    log.error("[INTERNAL_SERVER_ERROR]", ex);
    return ErrorResponseFactory.create(ex);
  }

  // 404 - NOT FOUND
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleNotFoundException(NotFoundException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }

  // 401 - UNAUTHORIZED
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionResponse handleUnauthorizedException(UnauthorizedException ex) {
    return ErrorResponseFactory.create(ex, messageSource);
  }
}
