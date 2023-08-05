package com.dudis.foodorders.domain.exception;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
