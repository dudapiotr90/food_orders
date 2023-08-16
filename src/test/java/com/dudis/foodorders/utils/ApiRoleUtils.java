package com.dudis.foodorders.utils;

import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

import java.util.List;

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


    public static ApiRole someApiRole1() {
        return ApiRole.builder()
            .apiRoleId(2)
            .role("OWNER")
            .build();
    }
    public static ApiRole someApiRole2() {
        return ApiRole.builder()
            .apiRoleId(3)
            .role("CUSTOMER")
            .build();
    }
    public static ApiRole someApiRole3() {
        return ApiRole.builder()
            .apiRoleId(4)
            .role("DEVELOPER")
            .build();
    }

    public static List<ApiRole> someApiRoles() {
        return List.of(someApiRole1(), someApiRole2(), someApiRole3());
    }

    public static List<ApiRoleEntity> someApiRoleEntities() {
        return List.of(someApiRoleEntity1(), someApiRoleEntity2(), someApiRoleEntity3());
    }
}
