package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Address;

public class AddressUtils {
    public static Address someAddress() {
        return Address.builder()
            .addressId(4)
            .city("Some City")
            .postalCode("00-000")
            .street("Some Street")
            .residenceNumber("1/77")
            .account(AccountUtils.someAccount1())
            .build();
    }
}
