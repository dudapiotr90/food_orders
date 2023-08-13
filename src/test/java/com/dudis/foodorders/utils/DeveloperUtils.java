package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.domain.Developer;

public class DeveloperUtils {
    public static Developer someDeveloper() {
        return Developer.builder()
            .name("someDeveloperName")
            .surname("someDeveloperSurname")
            .account(AccountUtils.someAccount().withRoleId(4))
            .build();
    }

    public static Developer someDeveloper2() {
        return Developer.builder()
            .name("anotherDeveloperName")
            .surname("anotherDeveloperSurname")
            .account(AccountUtils.someAccount().withEmail("another@email").withRoleId(4))
            .build();
    }
}
