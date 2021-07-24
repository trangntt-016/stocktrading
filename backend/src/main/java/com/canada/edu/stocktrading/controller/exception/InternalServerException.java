package com.canada.edu.stocktrading.controller.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message){
        super(message);
    }
}
