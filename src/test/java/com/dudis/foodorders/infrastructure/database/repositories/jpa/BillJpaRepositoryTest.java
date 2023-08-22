package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.*;
import com.dudis.foodorders.integration.configuration.PersistanceContainerTestConfiguration;
import com.dudis.foodorders.utils.BillUtils;
import com.dudis.foodorders.utils.OrderUtils;
import com.dudis.foodorders.utils.RestaurantUtils;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(PersistanceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BillJpaRepositoryTest {

    private BillJpaRepository billJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private OrderJpaRepository orderJpaRepository;

    @BeforeEach
    void checkDatabase() {
        assertThat(ownerJpaRepository.findAll().size()).isEqualTo(1);
        assertThat(customerJpaRepository.findAll().size()).isEqualTo(1);
    }
    @Test
    void assertThatRepositoryIsEmpty() {
        // Given, When, Then
        List<BillEntity> all = billJpaRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    void findByOwnerIdAndPayedWorksCorrectly() {
        // Given

        OwnerEntity owner = ownerJpaRepository.save(BillUtils.someBillToPersist1().getOwner());
        CustomerEntity customer = customerJpaRepository.save(BillUtils.someBillToPersist1().getCustomer());
        List<BillEntity> billsToPersist = getBillEntities(owner, customer);

        List<BillEntity> bills = billJpaRepository.saveAll(billsToPersist);
        assertThat(bills).hasSize(3);

        // When
        List<BillEntity> result = billJpaRepository.findByOwnerIdAndPayed(owner.getOwnerId(), false);
        List<BillEntity> result2 = billJpaRepository.findByOwnerIdAndPayed(123211, false);
        // Then
        assertThat(result2).isEmpty();
        assertThat(result).isEqualTo(bills);

    }

    @NotNull
    private static List<BillEntity> getBillEntities(OwnerEntity owner, CustomerEntity customer) {
        BillEntity bill1 = BillUtils.someBillToPersist1();
        BillEntity bill2 = BillUtils.someBillToPersist2();
        BillEntity bill3 = BillUtils.someBillToPersist3();
        return Stream.of(bill1, bill2, bill3)
            .map(b->b.withCustomer(customer).withOwner(owner))
            .toList();
    }

    @Test
    void findOrdersNotInProgressAndPayedAndNotRealizedWorksCorrectly() {
        // Given
        OwnerEntity owner = ownerJpaRepository.save(BillUtils.someBillToPersist1().getOwner());
        CustomerEntity customer = customerJpaRepository.save(BillUtils.someBillToPersist1().getCustomer());
        BillEntity bill1 = BillUtils.someBillToPersist1().withOwner(owner).withCustomer(customer).withPayed(true);
        BillEntity bill2 = BillUtils.someBillToPersist2().withOwner(owner).withCustomer(customer).withPayed(true);
        BillEntity bill3 = BillUtils.someBillToPersist3().withOwner(owner).withCustomer(customer).withPayed(true);
        RestaurantEntity restaurantToPersist1 = RestaurantUtils.someRestaurantToPersist1();
        restaurantToPersist1.setOwner(owner);
        RestaurantEntity restaurants = restaurantJpaRepository.save(restaurantToPersist1);
        OrderEntity order1 = OrderUtils.someOrderEntityToPersist1().withCustomer(customer).withRestaurant(restaurantToPersist1);
        OrderEntity order2 = OrderUtils.someOrderEntityToPersist1().withCustomer(customer).withRestaurant(restaurantToPersist1);
        OrderEntity order3 = OrderUtils.someOrderEntityToPersist1().withCustomer(customer).withRestaurant(restaurantToPersist1);
        List<OrderEntity> expected = orderJpaRepository.saveAll(List.of(order1, order2, order3));
        bill1.setOrder(order1);
        bill2.setOrder(order2);
        bill3.setOrder(order3);
        billJpaRepository.saveAll(List.of(bill1,bill2,bill3));
        // When
        List<OrderEntity> result = billJpaRepository.findOrdersNotInProgressAndPayedAndNotRealized(
            restaurants.getRestaurantId(), false, true, false);

        Assertions.assertThat(result).isEqualTo(expected);
    }
}