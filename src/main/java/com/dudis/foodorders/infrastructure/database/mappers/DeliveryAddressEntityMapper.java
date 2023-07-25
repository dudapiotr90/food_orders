package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryAddressEntityMapper {


    @Mapping(target = "restaurant",ignore = true)
    DeliveryAddress mapFromEntity(DeliveryAddressEntity deliveryAddressEntity);
    @Mapping(target = "restaurant",ignore = true)
    DeliveryAddressEntity mapToEntity(DeliveryAddress deliveryAddress);
}
