package com.canada.edu.stocktrading.client.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value={DuplicateEmailException.class})
    private ResponseEntity<Object>handleDuplicateEmailException(RuntimeException ex, WebRequest req){
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), HttpStatus.CONFLICT,req);
    }

    @ExceptionHandler(value={InternalServerException.class})
    private ResponseEntity<Object>handleInternalServerException(RuntimeException ex, WebRequest req){
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), HttpStatus.CONFLICT,req);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    private ResponseEntity<Object>handleBadRequestException(RuntimeException ex, WebRequest req){
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), HttpStatus.BAD_REQUEST,req);
    }
}
