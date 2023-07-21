package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {
    OwnerDTO mapToDTO(Owner owner);
}
