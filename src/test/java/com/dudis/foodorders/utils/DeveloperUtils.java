package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;

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
    public static DeveloperEntity someDeveloperEntity1() {
        return DeveloperEntity.builder()
            .name("anotherDeveloperName")
            .surname("anotherDeveloperSurname")
            .account(AccountUtils.someAccountEntity3())
            .build();
    }
    public static DeveloperEntity someDeveloperEntity2() {
        return DeveloperEntity.builder()
            .name("anotherDeveloperName")
            .surname("anotherDeveloperSurname")
            .account(AccountUtils.someAccountEntity2())
            .build();
    }
}
