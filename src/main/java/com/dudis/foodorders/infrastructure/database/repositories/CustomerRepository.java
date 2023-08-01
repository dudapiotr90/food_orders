package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CartJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import com.dudis.foodorders.infrastructure.security.Role;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.CustomerDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final AccountRepository accountRepository;
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final CartEntityMapper cartEntityMapper;



    @Override
    public ConfirmationToken registerCustomer(Customer customer) {
        ApiRoleEntity customerRole = apiRoleJpaRepository.findFirstByRole(Role.CUSTOMER.name());
        AccountEntity account = accountRepository.prepareAccountAccess(customer.getAccount(),customerRole);

        CustomerEntity customerToRegister = customerEntityMapper.mapToEntity(customer);
        customerToRegister.setAccount(account);

        customerJpaRepository.saveAndFlush(customerToRegister);

        return accountRepository.registerAccount(customerToRegister.getAccount(),customerRole);
    }

    @Override
    public Optional<Customer> findCustomerByAccountId(Integer accountId) {
        return customerJpaRepository.findByAccountId(accountId)
            .map(customerEntityMapper::mapFromEntity);
    }


    @Override
    public Cart addCart(Integer customerId) {
        CustomerEntity customerToUpdate = customerJpaRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer with id: [%s] doesn't exist".formatted(customerId)));
        CartEntity cartToAdd = CartEntity.builder().customerId(customerToUpdate.getCustomerId()).build();
        CartEntity saved = cartJpaRepository.saveAndFlush(cartToAdd);
        customerToUpdate.setCart(saved);
        customerJpaRepository.addCartToCustomer(saved,customerId);
        return cartEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Cart> findCartByCustomerId(Integer customerId) {
        return customerJpaRepository.findCartByCustomerId(customerId)
            .map(cartEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Customer> findCustomerById(Integer customerId) {
        return customerJpaRepository.findById(customerId)
            .map(customerEntityMapper::mapFromEntity);
    }

}
