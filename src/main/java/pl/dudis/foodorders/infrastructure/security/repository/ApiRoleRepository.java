package pl.dudis.foodorders.infrastructure.security.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.services.dao.ApiRoleDAO;

@Repository
@AllArgsConstructor
public class ApiRoleRepository implements ApiRoleDAO {
    private final ApiRoleJpaRepository apiRoleJpaRepository;
}
