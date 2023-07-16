package pl.dudis.foodorders.infrastructure.security.repository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.domain.exception.NotFoundException;
import pl.dudis.foodorders.infrastructure.database.mappers.AccountEntityMapper;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import pl.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import pl.dudis.foodorders.services.dao.AccountDAO;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccountRepository implements AccountDAO {

    private final AccountJpaRepository accountJpaRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final AccountManagerJpaRepository accountManagerJpaRepository;
    private final AccountEntityMapper accountEntityMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<Account> findByEmail(String userEmail) {
        return accountJpaRepository.findByEmail(userEmail).map(accountEntityMapper::mapFromEntity);
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
    public String registerAccount(AccountEntity account, ApiRoleEntity role) {
        Optional<AccountEntity> registeredAccount = accountJpaRepository.findByEmail(account.getEmail());
        if (registeredAccount.isEmpty()) {
            throw new NotFoundException("Failed to successfully registerAccount");
        }
        AccountManagerEntity accountManager = buildAccountManager(role, registeredAccount.get());

        accountManagerJpaRepository.saveAndFlush(accountManager);


    }

    private AccountManagerEntity buildAccountManager(ApiRoleEntity role, AccountEntity registeredAccount) {
        return AccountManagerEntity.builder()
            .accountId(registeredAccount.getAccountId())
            .apiRoleId(role.getApiRoleId())
            .build();
    }
}
