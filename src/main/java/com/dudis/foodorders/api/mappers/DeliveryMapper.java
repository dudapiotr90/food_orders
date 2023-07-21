package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.DeliveryDTO;
import com.dudis.foodorders.domain.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryMapper {
    DeliveryDTO mapToDTO(Delivery delivery);
}
