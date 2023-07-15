package pl.dudis.foodorders.infrastructure.database.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.domain.Customer;
import pl.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import pl.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import pl.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import pl.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import pl.dudis.foodorders.services.dao.CustomerDAO;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final ApiRoleEntityMapper apiRoleEntityMapper;

    @Override
    public void registerCustomer(Customer customer) {
        CustomerEntity customerToRegister = customerEntityMapper.mapToEntity(customer);
        CustomerEntity registeredCustomer = customerJpaRepository.saveAndFlush(customerToRegister);

    }

    private CustomerEntity mapToEntity(Customer customer) {
        return null;
    }
}
