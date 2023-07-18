package com.dudis.foodorders.infrastructure.security;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class ApiRole {
    Integer apiRoleId;
    String role;
}
