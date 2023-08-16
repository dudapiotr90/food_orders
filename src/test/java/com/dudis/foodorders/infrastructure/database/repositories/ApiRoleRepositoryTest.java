package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.repository.ApiRoleRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dudis.foodorders.utils.ApiRoleUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiRoleRepositoryTest {

    @InjectMocks
    private ApiRoleRepository apiRoleRepository;

    @Mock
    private ApiRoleJpaRepository apiRoleJpaRepository;
    @Mock
    private ApiRoleEntityMapper apiRoleEntityMapper;

    @Test
    void findRoleByIdWorksCorrectly() {
        // Given
        String expected = "Excepted Role";
        when(apiRoleJpaRepository.findRoleByApiRoleId(anyInt()))
            .thenReturn(expected);
        // When, Then
        String result = apiRoleRepository.findRoleById(3124);
        assertEquals(expected,result);
    }
    @Test
    void findApiRolesWorksCorrectly() {
        // Given
        when(apiRoleJpaRepository.findAll()).thenReturn(someApiRoleEntities());
        when(apiRoleEntityMapper.mapFromEntity(any(ApiRoleEntity.class)))
            .thenReturn(someApiRole1(), someApiRole2(), someApiRole3());
        List<ApiRole> expected = someApiRoles();
        // When, Then
        List<ApiRole> result = apiRoleRepository.findApiRoles();
        assertEquals(expected,result);
    }
}