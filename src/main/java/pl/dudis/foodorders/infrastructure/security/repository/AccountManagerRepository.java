package pl.dudis.foodorders.infrastructure.security.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountManagerRepository {

    private final AccountManagerJpaRepository accountManagerJpaRepository;
}
