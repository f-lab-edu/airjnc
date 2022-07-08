package com.airjnc.common.exception.controller;

import com.airjnc.common.exception.InvalidFormException;
import com.airjnc.common.exception.code.ErrorCode;
import com.airjnc.common.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExeptionHandler extends ResponseEntityExceptionHandler {

    private HttpStatus getHttpStatus(ErrorCode errorCode) {
        return HttpStatus.resolve(errorCode.getStatus());
    }

    private ErrorResponse setErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .build();
    }

    @ExceptionHandler(InvalidFormException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormException(InvalidFormException ex) {

        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse = setErrorResponse(errorCode);
        errorResponse.setCustomFieldErrors(ex.getErrors().getFieldErrors());

        return ResponseEntity.status(getHttpStatus(errorCode)).body(errorResponse);
    }
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
//        ErrorCode errorCode = e.getErrorCode();
//
//        ErrorResponse response = setErrorResponse(errorCode, e.toString());
//
//        return ResponseEntity.status(getHttpStatus(errorCode)).body(response);
//    }
}
