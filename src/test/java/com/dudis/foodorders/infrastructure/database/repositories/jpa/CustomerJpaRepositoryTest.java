package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.integration.configuration.PersistanceContainerTestConfiguration;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.CustomerUtils;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(PersistanceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerJpaRepositoryTest {

    private CustomerJpaRepository customerJpaRepository;
    private CartJpaRepository cartJpaRepository;

    @Test
    void findByAccountIdWorksCorrectly() {
        // Given
        CustomerEntity customer = getProperCustomer();
        CustomerEntity saved = customerJpaRepository.saveAndFlush(customer);
        Integer accountId = saved.getAccount().getAccountId();

        // When
        Optional<CustomerEntity> result = customerJpaRepository.findByAccountId(accountId);
        Optional<CustomerEntity> result2 = customerJpaRepository.findByAccountId(54);
        // Then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result2).isEmpty();
        Assertions.assertThat(result.get()).isEqualTo(customer);
    }
    @Test
    void findCartByCustomerIdAndAddCartWorksCorrectly() {
        // Given
        CustomerEntity customer = getProperCustomer();
        CustomerEntity saved = customerJpaRepository.saveAndFlush(customer);
        Assertions.assertThat(saved).hasFieldOrPropertyWithValue("cart", null);
        CartEntity cart = cartJpaRepository.save(CartEntity.builder().customerId(saved.getCustomerId()).build());
        customer.setCart(cart);
        customerJpaRepository.addCartToCustomer(cart,saved.getCustomerId());

        // When
        Optional<CartEntity> result = customerJpaRepository.findCartByCustomerId(saved.getCustomerId());
        Optional<CartEntity> result2 = customerJpaRepository.findCartByCustomerId(54);
        // Then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result2).isEmpty();
        Assertions.assertThat(result.get()).isEqualTo(customer.getCart());
        Assertions.assertThat(saved).hasFieldOrPropertyWithValue("cart",result.get());
    }

    private static CustomerEntity getProperCustomer() {
        CustomerEntity customer = CustomerUtils.someCustomerEntity1();
        customer.setAccount(AccountUtils.someAccountToPersist());
        customer.setCustomerId(null);
        return customer;
    }
}