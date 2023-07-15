package pl.dudis.foodorders.infrastructure.security.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.infrastructure.database.mappers.AccountEntityMapper;
import pl.dudis.foodorders.services.dao.AccountDAO;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccountRepository implements AccountDAO {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountEntityMapper accountEntityMapper;
    @Override
    public Optional<Account> findByEmail(String userEmail) {
        return accountJpaRepository.findByEmail(userEmail).map(accountEntityMapper::mapFromEntity);
    }
}
