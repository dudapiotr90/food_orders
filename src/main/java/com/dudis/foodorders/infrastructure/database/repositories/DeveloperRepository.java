package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeveloperEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeveloperJpaRepository;
import com.dudis.foodorders.infrastructure.security.Role;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.DeveloperDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeveloperRepository implements DeveloperDAO {

    private final DeveloperJpaRepository developerJpaRepository;
    private final AccountRepository accountRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final DeveloperEntityMapper developerEntityMapper;
    @Override
    public ConfirmationToken registerDeveloper(Developer developer) {
        ApiRoleEntity developerRole = apiRoleJpaRepository.findFirstByRole(Role.DEVELOPER.name());
        AccountEntity account = accountRepository.prepareAccountAccess(developer.getAccount(),developerRole);

        DeveloperEntity developerToRegister = developerEntityMapper.mapToEntity(developer);
        developerToRegister.setAccount(account);

        developerJpaRepository.saveAndFlush(developerToRegister);

        return accountRepository.registerAccount(developerToRegister.getAccount(),developerRole);
    }
}
