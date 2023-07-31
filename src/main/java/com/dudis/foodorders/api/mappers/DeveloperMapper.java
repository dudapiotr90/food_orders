package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.DeveloperDTO;
import com.dudis.foodorders.domain.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = OwnerMapper.class)
public interface DeveloperMapper {

    @Mapping(source = "account",target = "accountId",qualifiedByName = "getAccountId")
    DeveloperDTO mapToDTO(Developer developer);
}
