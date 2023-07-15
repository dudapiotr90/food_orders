package pl.dudis.foodorders.services.dao;

import pl.dudis.foodorders.domain.Account;

import java.util.Optional;

public interface AccountDAO {
    Optional<Account> findByEmail(String userEmail);
}
