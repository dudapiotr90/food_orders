package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.domain.DeliveryAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {


    DeliveryAddressDTO mapToDTO(DeliveryAddress deliveryAddress);
}
