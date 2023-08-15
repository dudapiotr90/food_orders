package com.dudis.foodorders.utils;

import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

public class ApiRoleUtils {
    public static ApiRoleEntity someApiRoleEntity1() {
        return ApiRoleEntity.builder()
            .apiRoleId(2)
            .role("OWNER")
            .build();
    }
    public static ApiRoleEntity someApiRoleEntity2() {
        return ApiRoleEntity.builder()
            .apiRoleId(3)
            .role("CUSTOMER")
            .build();
    }
    public static ApiRoleEntity someApiRoleEntity3() {
        return ApiRoleEntity.builder()
            .apiRoleId(4)
            .role("DEVELOPER")
            .build();
    }
}
