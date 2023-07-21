package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.mappers.AddressEntityMapper;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import com.dudis.foodorders.infrastructure.security.Role;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.CustomerDAO;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;


    private final AccountRepository accountRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final ApiRoleEntityMapper apiRoleEntityMapper;
    private final AddressEntityMapper addressEntityMapper;


    @Override
    public ConfirmationToken registerCustomer(Customer customer) {
//        AccountEntity account = accountEntityMapper.mapToEntity(customer.getAccount());
        ApiRoleEntity customerRole = apiRoleJpaRepository.findFirstByRole(Role.CUSTOMER.name());
        AccountEntity account = accountRepository.prepareAccountAccess(customer.getAccount(),customerRole);


//        AccountEntity account = accountRepository.mapToEntity(customer.getAccount());

        CustomerEntity customerToRegister = customerEntityMapper.mapToEntity(customer);
        customerToRegister.setAccount(account);

//        apiRoleJpaRepository.saveAndFlush(customerToRegister.getAccount().getRole());

        customerJpaRepository.saveAndFlush(customerToRegister);

        return accountRepository.registerAccount(customerToRegister.getAccount(),customerRole);

    }

}
