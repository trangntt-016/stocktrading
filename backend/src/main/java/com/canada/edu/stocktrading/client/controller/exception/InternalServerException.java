package com.canada.edu.stocktrading.client.controller.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message){
        super(message);
    }
}
