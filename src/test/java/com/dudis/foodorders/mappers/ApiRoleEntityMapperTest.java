package com.dudis.foodorders.mappers;

import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.infrastructure.database.entities.AddressEntity;
import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.utils.AddressUtils;
import com.dudis.foodorders.utils.ApiRoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ApiRoleEntityMapperTest {

    private ApiRoleEntityMapper apiRoleEntityMapper= Mappers.getMapper(ApiRoleEntityMapper.class);

    @Test
    void mapToEntityWorksCorrectly() {
        // Given
        ApiRole role = ApiRoleUtils.someApiRole1();

        // When
        ApiRoleEntity model = apiRoleEntityMapper.mapToEntity(role);

        // Then
        Assertions.assertEquals(role.getRole(),model.getRole());
        Assertions.assertEquals(role.getApiRoleId(),model.getApiRoleId());
    }

    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        ApiRoleEntity role = ApiRoleUtils.someApiRoleEntity1();

        // When
        ApiRole model = apiRoleEntityMapper.mapFromEntity(role);

        // Then
        Assertions.assertEquals(role.getRole(),model.getRole());
        Assertions.assertEquals(role.getApiRoleId(),model.getApiRoleId());
    }
}
