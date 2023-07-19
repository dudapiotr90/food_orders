package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OwnerJpaRepository;
import com.dudis.foodorders.infrastructure.security.Role;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.OwnerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final AccountRepository accountRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public ConfirmationToken registerOwner(Owner owner) {
        ApiRoleEntity ownerRole = apiRoleJpaRepository.findFirstByRole(Role.OWNER.name());
        AccountEntity account = accountRepository.prepareAccountAccess(owner.getAccount(),ownerRole);

        OwnerEntity ownerToRegister = ownerEntityMapper.mapToEntity(owner);
        ownerToRegister.setAccount(account);

        ownerJpaRepository.saveAndFlush(ownerToRegister);

        return accountRepository.registerAccount(ownerToRegister.getAccount(),ownerRole);
    }
}
