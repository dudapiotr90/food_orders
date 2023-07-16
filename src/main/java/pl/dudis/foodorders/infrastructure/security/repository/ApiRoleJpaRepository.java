package pl.dudis.foodorders.infrastructure.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

@Repository
public interface ApiRoleJpaRepository extends JpaRepository<ApiRoleEntity,Integer> {

    ApiRoleEntity findFirstByRole(String role);
}
