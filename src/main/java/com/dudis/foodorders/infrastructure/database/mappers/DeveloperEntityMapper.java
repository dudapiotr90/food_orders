package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeveloperEntityMapper {

    @Mapping(target = "account",ignore = true)
    DeveloperEntity mapToEntity(Developer developer);
    @Mapping(target = "account.address",ignore = true)
    Developer mapFromEntity(DeveloperEntity developerEntity);
}
