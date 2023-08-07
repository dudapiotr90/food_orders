package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = AccountMapper.class)
public interface OwnerMapper {
    @Mapping(target = "accountId", source = "account", qualifiedByName = "getAccountId")
    OwnerDTO mapToDTO(Owner owner);



    @Mapping(target = "account",ignore = true)
    @Mapping(target = "restaurants",ignore = true)
    @Mapping(target = "bills",ignore = true)
    Owner mapFromDTO(OwnerDTO ownerDTO);
}
