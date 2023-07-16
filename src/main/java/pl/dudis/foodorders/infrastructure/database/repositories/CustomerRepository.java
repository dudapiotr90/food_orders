package pl.dudis.foodorders.infrastructure.database.repositories;

import lombok.AllArgsConstructor;
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
import pl.dudis.foodorders.infrastructure.security.repository.ApiRoleJpaRepository;
import pl.dudis.foodorders.services.dao.CustomerDAO;

import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final AccountManagerJpaRepository accountManagerJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final ApiRoleEntityMapper apiRoleEntityMapper;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public void registerCustomer(Customer customer) {
//        AddressEntity address = addressEntityMapper.mapToEntity(customer.getAccount().getAddress());
        AccountEntity account = accountEntityMapper.mapToEntity(customer.getAccount());

        ApiRoleEntity customerRole = apiRoleJpaRepository.findFirstByRole(Role.CUSTOMER.name());



        CustomerEntity customerToRegister = customerEntityMapper.mapToEntity(customer);
        customerToRegister.setAccount(account.setRoles(Set.of(customerRole));

//        apiRoleJpaRepository.saveAndFlush(customerToRegister.getAccount().getRole());
        CustomerEntity registeredCustomer = customerJpaRepository.saveAndFlush(customerToRegister);
        Optional<AccountEntity> registeredAccount = accountJpaRepository.findByEmail(account.getEmail());
        if (registeredAccount.isEmpty()) {
            throw new NotFoundException("Failed to successfully registerAccount");
        }
        AccountManagerEntity accountManager = AccountManagerEntity.builder()
            .accountId(registeredAccount.get().getAccountId())
            .apiRoleId(customerRole.getApiRoleId())
            .build();

        accountManagerJpaRepository.saveAndFlush(accountManager);

    }

    private CustomerEntity mapToEntity(Customer customer) {
        return null;
    }
}
