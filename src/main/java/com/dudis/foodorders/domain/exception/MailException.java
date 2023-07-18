package com.dudis.foodorders.domain.exception;

public class MailException extends RuntimeException{

    public MailException(String message) {
        super(message);
    }
}
