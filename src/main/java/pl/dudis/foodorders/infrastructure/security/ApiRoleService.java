package pl.dudis.foodorders.infrastructure.security;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ApiRoleService {

    public List<Role> findAvailableApiRoles() {
        return List.of(Role.values());
    }
}
