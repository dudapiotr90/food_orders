package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {

    @Named("mapOwnerToEntity")
    @Mapping(target = "restaurants",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "account",ignore = true)
    OwnerEntity mapToEntity(Owner owner);

    @Mapping(target = "restaurants",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "account.address",ignore = true)
    Owner mapFromEntity(OwnerEntity owner);
}
