package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Local;
import com.dudis.foodorders.infrastructure.database.entities.LocalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalEntityMapper {
    @Mapping(target = "owner",ignore = true)
    @Mapping(target = "deliveries",ignore = true)
    @Mapping(target = "deliveryAddresses",ignore = true)
    @Mapping(target = "orders",ignore = true)
    Local mapFromEntity(LocalEntity local);
}
