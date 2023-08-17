package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddress1;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddressDTO1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryAddressMapperTest {

    private DeliveryAddressMapper deliveryAddressMapper;
    @Test
    void mapToDTOWorksCorrectly() {
        // Given
        DeliveryAddress expected = someDeliveryAddress1();

        // When
        DeliveryAddressDTO model = deliveryAddressMapper.mapToDTO(expected);

        // Then
        Assertions.assertEquals(expected.getDeliveryAddressId(),model.getDeliveryAddressId());
        Assertions.assertEquals(expected.getCity(),model.getCity());
        Assertions.assertEquals(expected.getStreet(),model.getStreet());
        Assertions.assertEquals(expected.getPostalCode(),model.getPostalCode());

    }
    @Test
    void mapFromDTOEntityWorksCorrectly() {
        DeliveryAddressDTO expected = someDeliveryAddressDTO1();

        // When
        DeliveryAddress model = deliveryAddressMapper.mapFromDTO(expected);

        // Then
        Assertions.assertEquals(expected.getDeliveryAddressId(),model.getDeliveryAddressId());
        Assertions.assertEquals(expected.getCity(),model.getCity());
        Assertions.assertEquals(expected.getStreet(),model.getStreet());
        Assertions.assertEquals(expected.getPostalCode(),model.getPostalCode());
    }

}