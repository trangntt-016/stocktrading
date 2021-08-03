package com.canada.edu.stocktrading.api.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message){
        super(message);
    }
}
