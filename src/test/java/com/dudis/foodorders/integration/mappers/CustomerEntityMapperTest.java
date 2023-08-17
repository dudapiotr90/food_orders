package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.CustomerUtils;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerEntityMapperTest {

    private CustomerEntityMapper customerEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        CustomerEntity expected = CustomerUtils.someCustomerEntity1();

        // When
        Customer model = customerEntityMapper.mapFromEntity(expected);

        // Then
        EntityAssertionsUtils.assertCustomerFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Customer expected = CustomerUtils.someCustomer();

        // When
        CustomerEntity model = customerEntityMapper.mapToEntity(expected);

        // Then
        EntityAssertionsUtils.assertCustomerToEntityEquals(expected, model);
    }

}