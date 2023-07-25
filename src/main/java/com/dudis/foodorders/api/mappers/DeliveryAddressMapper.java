package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.domain.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAddressMapper {

    DeliveryAddressDTO mapToDTO(DeliveryAddress deliveryAddress);

    DeliveryAddress mapFromDTO(DeliveryAddressDTO deliveryAddressDTO);
}
