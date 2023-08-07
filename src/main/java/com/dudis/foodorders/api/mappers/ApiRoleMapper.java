package com.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleMapper {

//    @Mapping(source = "roles.ADMIN",ignore = true)
//    ApiRoleDTO mapToDTO(ApiRole role);

//    @Named("mapApiRole")
//    ApiRole mapFromDTO(ApiRoleDTO apiRole);
}
