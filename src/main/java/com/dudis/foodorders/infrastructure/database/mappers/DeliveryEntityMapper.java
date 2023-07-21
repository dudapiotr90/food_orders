package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Delivery;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryEntityMapper {

    @Mapping(target = "local",ignore = true)
    @Mapping(target = "owner",ignore = true)
    Delivery mapFromEntity(DeliveryEntity delivery);
}
