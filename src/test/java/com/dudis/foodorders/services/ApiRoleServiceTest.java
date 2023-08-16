package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.ApiRoleService;
import com.dudis.foodorders.services.dao.ApiRoleDAO;
import com.dudis.foodorders.utils.ApiRoleUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApiRoleServiceTest {

    @InjectMocks
    private ApiRoleService apiRoleService;

    @Mock
    private ApiRoleDAO apiRoleDAO;

    @Test
    void findApiRolesWorksCorrectly() {
        // Given
        List<ApiRole> expected = ApiRoleUtils.someApiRoles();
        Mockito.when(apiRoleDAO.findApiRoles()).thenReturn(expected);

        // When, Then
        List<ApiRole> result = apiRoleService.findApiRoles();
        assertEquals(expected,result);
    }
}