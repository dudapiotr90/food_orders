package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import com.dudis.foodorders.services.dao.ApiRoleDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApiRoleService {

    private final ApiRoleDAO apiRoleDAO;

    public List<Role> findAvailableApiRoles() {
        return List.of(Role.values());
    }

    public String findRoleById(Integer roleId) {
        return apiRoleDAO.findRoleById(roleId);
    }


    public List<ApiRole> findApiRoles() {
        return apiRoleDAO.findApiRoles();
    }
}
