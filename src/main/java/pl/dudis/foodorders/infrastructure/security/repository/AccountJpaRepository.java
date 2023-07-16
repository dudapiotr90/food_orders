package pl.dudis.foodorders.infrastructure.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity,Integer> {

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByLogin(String login);
}
