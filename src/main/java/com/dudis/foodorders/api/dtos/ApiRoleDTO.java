package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ApiRoleDTO {
    private Set<RoleDTO> roles;
}
