package com.dudis.foodorders.services;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class RandomUUIDGenerator {
    public String generateUniqueCode() {
        return UUID.randomUUID().toString();
    }
}
