package pl.dudis.foodorders.infrastructure.database.repositories;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.api.mappers.AddressEntityMapper;
import pl.dudis.foodorders.domain.Customer;
import pl.dudis.foodorders.domain.exception.NotFoundException;
import pl.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import pl.dudis.foodorders.infrastructure.database.mappers.AccountEntityMapper;
import pl.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import pl.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import pl.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import pl.dudis.foodorders.infrastructure.security.Role;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import pl.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import pl.dudis.foodorders.infrastructure.security.repository.AccountJpaRepository;
import pl.dudis.foodorders.infrastructure.security.repository.AccountManagerJpaRepository;
import pl.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import pl.dudis.foodorders.infrastructure.security.repository.ApiRoleJpaRepository;
import pl.dudis.foodorders.services.dao.CustomerDAO;

import java.util.Optional;
import java.util.Set;

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
    public void registerCustomer(Customer customer) {
//        AccountEntity account = accountEntityMapper.mapToEntity(customer.getAccount());
        ApiRoleEntity customerRole = apiRoleJpaRepository.findFirstByRole(Role.CUSTOMER.name());
        AccountEntity account = accountRepository.prepareAccountAccess(customer.getAccount(),customerRole);


//        AccountEntity account = accountRepository.mapToEntity(customer.getAccount());

        CustomerEntity customerToRegister = customerEntityMapper.mapToEntity(customer);
        customerToRegister.setAccount(account);

//        apiRoleJpaRepository.saveAndFlush(customerToRegister.getAccount().getRole());

        customerJpaRepository.saveAndFlush(customerToRegister);

        String token = accountRepository.registerAccount(customerToRegister.getAccount(),customerRole);

    }

}
