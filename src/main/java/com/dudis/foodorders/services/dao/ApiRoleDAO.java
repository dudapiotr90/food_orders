package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.infrastructure.security.ApiRole;

import java.util.List;

public interface ApiRoleDAO {
    String findRoleById(Integer roleId);

    List<ApiRole> findApiRoles();

}
