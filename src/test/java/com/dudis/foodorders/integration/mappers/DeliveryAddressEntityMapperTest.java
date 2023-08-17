package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeliveryAddressEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddress1;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddressEntity1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryAddressEntityMapperTest {

    private DeliveryAddressEntityMapper deliveryAddressEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        DeliveryAddressEntity expected = someDeliveryAddressEntity1();

        // When
        DeliveryAddress model = deliveryAddressEntityMapper.mapFromEntity(expected);

        // Then
        Assertions.assertTrue(EntityAssertionsUtils.assertDeliveryAddressFromEntityEquals(expected, model));

    }
    @Test
    void mapToEntityWorksCorrectly() {
        DeliveryAddress expected = someDeliveryAddress1();

        // When
        DeliveryAddressEntity model = deliveryAddressEntityMapper.mapToEntity(expected);

        // Then
        Assertions.assertTrue(EntityAssertionsUtils.assertDeliveryAddressToEntityEquals(expected, model));
    }

}