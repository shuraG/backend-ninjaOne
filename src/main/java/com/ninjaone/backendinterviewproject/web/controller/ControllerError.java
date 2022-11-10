package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.NotFoundException;
import com.ninjaone.backendinterviewproject.domain.BusinessException;
import com.ninjaone.backendinterviewproject.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerError {

    @ExceptionHandler({BusinessException.class})
    public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        var body = new ErrorResponse(ex.getMessage());
        if (ex instanceof NotFoundException) {
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
