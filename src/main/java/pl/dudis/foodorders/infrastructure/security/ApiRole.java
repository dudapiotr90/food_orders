package pl.dudis.foodorders.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class ApiRole {
    Integer apiRoleId;
    String role;
}
