package com.canada.edu.stocktrading.api.exception;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
