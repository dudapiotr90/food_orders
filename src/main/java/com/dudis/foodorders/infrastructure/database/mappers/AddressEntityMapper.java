package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.infrastructure.database.entities.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {
    @Mapping(target = "account",ignore = true)
    AddressEntity mapToEntity(Address address);
}
