package com.canada.edu.stocktrading.api.exception;

import com.canada.edu.stocktrading.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    private final ResponseFactory responseFactory;

    @Autowired
    public ExceptionHandling(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @ExceptionHandler(value={DuplicateEmailException.class})
    private ResponseEntity<?>handleDuplicateEmailException(RuntimeException ex, WebRequest req) {
        return this.responseFactory.duplicateData(ex.getMessage());
    }

    @ExceptionHandler(value={InternalServerException.class})
    private ResponseEntity<?>handleInternalServerException(RuntimeException ex, WebRequest req) {
        return this.responseFactory.internalServerError(ex.getMessage());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    private ResponseEntity<?>handleBadRequestException(RuntimeException ex, WebRequest req) {
        return this.responseFactory.badRequest(ex.getMessage());
    }
}
