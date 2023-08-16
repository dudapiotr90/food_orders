package com.dudis.foodorders.mappers;


import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.infrastructure.database.entities.AddressEntity;
import com.dudis.foodorders.infrastructure.database.mappers.AddressEntityMapper;
import com.dudis.foodorders.utils.AddressUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class AddressEntityMapperTest {

    private AddressEntityMapper addressEntityMapper = Mappers.getMapper(AddressEntityMapper.class);


    @Test
    void addressEntityMapperWorksCorrectly() {
        // Given
        Address address = AddressUtils.someAddress();
        Address expected = address.withAccount(null);

        // When
        AddressEntity model = addressEntityMapper.mapToEntity(address);

        // Then
        Assertions.assertEquals(expected.getAddressId(),model.getAddressId());
        Assertions.assertEquals(expected.getCity(),model.getCity());
        Assertions.assertEquals(expected.getStreet(),model.getStreet());
        Assertions.assertEquals(expected.getPostalCode(),model.getPostalCode());
        Assertions.assertEquals(expected.getResidenceNumber(),model.getResidenceNumber());
        Assertions.assertNull(model.getAccount());

    }
}
