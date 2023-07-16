package pl.dudis.foodorders.services.dao;

import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

import java.util.Optional;

public interface AccountDAO {
    Optional<Account> findByEmail(String userEmail);

//    AccountEntity mapToEntity(Account account);

    AccountEntity prepareAccountAccess(Account accountToPrepare, ApiRoleEntity customerRole);

    void registerAccount(AccountEntity account, ApiRoleEntity customerRole);
}
