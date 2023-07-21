package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {

    @Mapping(target = "locals",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "deliveries",ignore = true)
    @Mapping(target = "account",ignore = true)
    OwnerEntity mapToEntity(Owner owner);


    Owner mapFromEntity(OwnerEntity owner);
}
