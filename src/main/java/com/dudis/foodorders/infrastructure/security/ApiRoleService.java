package com.dudis.foodorders.infrastructure.security;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiRoleService {

    public List<Role> findAvailableApiRoles() {
        return List.of(Role.values());
    }
}
