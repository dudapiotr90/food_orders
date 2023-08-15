package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CartJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.CartUtils;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dudis.foodorders.utils.ApiRoleUtils.someApiRoleEntity2;
import static com.dudis.foodorders.utils.CartUtils.someCart;
import static com.dudis.foodorders.utils.CartUtils.someCartEntity;
import static com.dudis.foodorders.utils.CustomerUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @InjectMocks
    private CustomerRepository customerRepository;

    @Mock
    private CustomerJpaRepository customerJpaRepository;
    @Mock
    private CartJpaRepository cartJpaRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ApiRoleJpaRepository apiRoleJpaRepository;
    @Mock
    private CustomerEntityMapper customerEntityMapper;
    @Mock
    private CartEntityMapper cartEntityMapper;

    @Test
    void registerCustomerWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        Customer someCustomer = someCustomer();
        when(apiRoleJpaRepository.findFirstByRole("CUSTOMER")).thenReturn(someApiRoleEntity2());
        when(accountRepository.prepareAccountAccess(any(Account.class), any(ApiRoleEntity.class)))
            .thenReturn(AccountUtils.someAccountEntity1());
        CustomerEntity someCustomerEntity = someCustomerEntity1();
        when(customerEntityMapper.mapToEntity(any(Customer.class))).thenReturn(someCustomerEntity);
        when(customerJpaRepository.saveAndFlush(someCustomerEntity)).thenReturn(someCustomerEntity);
        when(accountRepository.registerAccount(someCustomerEntity.getAccount(), someApiRoleEntity2()))
            .thenReturn(expected);

        // When
        ConfirmationToken result = customerRepository.registerCustomer(someCustomer);
        // Then
        assertEquals(expected,result);

    }
    @Test
    void findCustomerByAccountIdWorksCorrectly() {
        // Given
        Optional<Customer> expected = Optional.of(someCustomer());
        when(customerJpaRepository.findByAccountId(anyInt())).thenReturn(Optional.of(someCustomerEntity1()));
        when(customerEntityMapper.mapFromEntity(any(CustomerEntity.class))).thenReturn(someCustomer());

        // When
        Optional<Customer> result = customerRepository.findCustomerByAccountId(54);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void addCartWorksCorrectly() {
        // Given
        Integer someId = 432;
        Cart expected = someCart();
        when(customerJpaRepository.findById(anyInt()))
            .thenReturn(Optional.of(someCustomerEntity1()))
            .thenReturn(Optional.empty());
        when(cartJpaRepository.saveAndFlush(any(CartEntity.class)))
            .thenReturn(CartUtils.someCartEntity());
        doNothing().when(customerJpaRepository).addCartToCustomer(someCartEntity(),someId);
        when(cartEntityMapper.mapFromEntity(someCartEntity())).thenReturn(expected);

        // When
        Cart result = customerRepository.addCart(someId);
        // Then
        assertEquals(expected, result);

    }
    @Test
    void findCartByCustomerIdWorksCorrectly() {
        // Given
        Optional<Cart> expected = Optional.of(someCart());
        when(customerJpaRepository.findCartByCustomerId(anyInt())).thenReturn(Optional.of(someCartEntity()));
        when(cartEntityMapper.mapFromEntity(any(CartEntity.class))).thenReturn(someCart());

        // When
        Optional<Cart> result = customerRepository.findCartByCustomerId(54);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void findPagedCustomersWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        Page<CustomerEntity> customerEntities = new PageImpl<>(someCustomerEntities());
        Page<Customer> expected = new PageImpl<>(List.of(someCustomer(),someCustomer2()));
        when(customerJpaRepository.findAll(any(Pageable.class))).thenReturn(customerEntities);
        when(customerEntityMapper.mapFromEntity(any(CustomerEntity.class)))
            .thenReturn(someCustomer(),someCustomer2());

        // When
        Page<Customer> result = customerRepository.findPagedCustomers(pageable);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void findCustomerByIdWorksCorrectly() {
        // Given
        Optional<Customer> expected = Optional.of(someCustomer());
        when(customerJpaRepository.findById(anyInt())).thenReturn(Optional.of(someCustomerEntity1()));
        when(customerEntityMapper.mapFromEntity(any(CustomerEntity.class))).thenReturn(someCustomer());

        // When
        Optional<Customer> result = customerRepository.findCustomerById(54);
        // Then
        assertEquals(expected,result);
    }
}