package com.airjnc.common.exception.controller;

import com.airjnc.common.exception.InvalidFormException;
import com.airjnc.common.exception.code.ErrorCode;
import com.airjnc.common.dto.ErrorResponseBodyHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(InvalidFormException.class)
    public ResponseEntity<ErrorResponseBodyHandler> handleInvalidFormException(InvalidFormException ex) {

        ErrorCode errorCode = ex.getErrorCode();
        
        ErrorResponseBodyHandler errorResponse = new ErrorResponseBodyHandler();
        errorResponse.setMessage(errorCode.getMessage());
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

    private HttpStatus getHttpStatus(ErrorCode errorCode) {
        return HttpStatus.resolve(errorCode.getStatus());
    }
}
