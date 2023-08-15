package com.dudis.foodorders.services;

import java.util.UUID;

public class RandomUUIDGenerator {
    public String generateUniqueCode() {
        return UUID.randomUUID().toString();
    }
}
