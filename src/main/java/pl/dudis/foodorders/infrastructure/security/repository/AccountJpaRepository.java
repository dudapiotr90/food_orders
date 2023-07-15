package pl.dudis.foodorders.infrastructure.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountEntity,Integer> {

    Optional<AccountEntity> findByEmail(String email);
}
