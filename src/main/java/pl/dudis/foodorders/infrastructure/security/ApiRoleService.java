package pl.dudis.foodorders.infrastructure.security;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiRoleService {

    public List<ApiRole> findAvailableApiRoles() {
        return List.of(ApiRole.values());
    }
}
