package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.util.Optional;

public interface AccountDAO {
    Optional<Account> findByEmail(String userEmail);

    AccountEntity prepareAccountAccess(Account accountToPrepare, ApiRoleEntity customerRole);

    ConfirmationToken registerAccount(AccountEntity account, ApiRoleEntity customerRole);

    void enableAccount(Integer accountId);

    Optional<Account> findByLogin(String login);
}
