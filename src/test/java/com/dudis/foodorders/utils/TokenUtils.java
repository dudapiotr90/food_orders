package com.dudis.foodorders.utils;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TokenUtils {

    public static ConfirmationToken someToken() {
        return ConfirmationToken.builder()
            .token("a5e6e2a7-3b57-4b8c-9644-d9c243a3c4c1")
            .createdAt(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .accountEntity(AccountUtils.someAccountEntity1())
            .expiresAt(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC).plusHours(1))
            .build();
    }
}
