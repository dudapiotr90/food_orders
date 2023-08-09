package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.*;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountManagerJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.AccountDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AccountRepository implements AccountDAO {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountManagerJpaRepository accountManagerJpaRepository;
    private final AccountEntityMapper accountEntityMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ApiRoleJpaRepository apiRoleJpaRepository;

    @Override
    public Optional<Account> findByEmail(String userEmail) {
        return accountJpaRepository.findByEmail(userEmail).map(accountEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Account> findByLogin(String login) {
        return accountJpaRepository.findByLogin(login)
            .map(accountEntityMapper::mapFromEntity);
    }

    @Override
    public Integer findByRole(String role) {
        return apiRoleJpaRepository.findByRole(role);
    }

    @Override
    public Optional<Account> findById(Integer accountId) {
        return accountJpaRepository.findById(accountId)
            .map(accountEntityMapper::mapFromEntity);
    }

    @Override
    public long countAllAccounts() {
        return accountJpaRepository.count();
    }

    @Override
    public AccountEntity prepareAccountAccess(Account accountToPrepare, ApiRoleEntity role) {
        AccountEntity account = accountEntityMapper.mapToEntity(accountToPrepare);
        account.setRoleId(role.getApiRoleId());
        account.setStatus(true);
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return account;
    }

    @Override
    public ConfirmationToken registerAccount(AccountEntity account, ApiRoleEntity role) {
        Optional<AccountEntity> registeredAccount = accountJpaRepository.findByEmail(account.getEmail());
        if (registeredAccount.isEmpty()) {
            throw new RegistrationException("Failed to successfully registerAccount");
        }
        AccountManagerEntity accountManager = buildAccountManager(role, registeredAccount.get());
        accountManagerJpaRepository.saveAndFlush(accountManager);

        return prepareActivationToken(account);
    }

    @Override
    public void enableAccount(Integer accountId) {
        accountJpaRepository.enableAccount(accountId);
    }

    private AccountManagerEntity buildAccountManager(ApiRoleEntity role, AccountEntity registeredAccount) {
        return AccountManagerEntity.builder()
            .accountId(registeredAccount.getAccountId())
            .apiRoleId(role.getApiRoleId())
            .build();
    }

    private ConfirmationToken prepareActivationToken(AccountEntity account) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        return ConfirmationToken.builder()
            .token(UUID.randomUUID().toString())
            .createdAt(createdAt)
            .expiresAt(createdAt.plusHours(1))
            .accountEntity(account)
            .build();
    }
}
