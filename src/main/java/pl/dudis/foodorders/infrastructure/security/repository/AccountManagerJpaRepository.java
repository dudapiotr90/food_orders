package pl.dudis.foodorders.infrastructure.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;

@Repository
public interface AccountManagerJpaRepository extends JpaRepository<AccountManagerEntity,Integer> {
}
