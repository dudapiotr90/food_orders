package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.domain.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {

    @Mapping(source = "deliveryAddressId", target = "deliveryId")
    DeliveryAddressDTO mapToDTO(DeliveryAddress deliveryAddress);
}
