package com.dudis.foodorders.infrastructure.security;

public class AuthorityException extends RuntimeException{
    public AuthorityException(String message) {
        super(message);
    }
}
