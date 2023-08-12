package com.dudis.foodorders.services.utils;

import com.dudis.foodorders.api.dtos.OwnerDTO;

public class OwnerUtils {

    public static OwnerDTO someOwnerDTO() {
        return OwnerDTO.builder()
            .ownerId(1)
            .name("name")
            .surname("surname")
            .accountId(1)
            .build();
    }
}
