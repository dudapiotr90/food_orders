package pl.dudis.foodorders.infrastructure.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN,
    CUSTOMER,
    OWNER,
    DEVELOPER
}
