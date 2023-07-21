package com.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.dudis.foodorders.api.dtos.ApiRoleDTO;
import com.dudis.foodorders.infrastructure.security.ApiRole;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleMapper {

//    @Mapping(source = "roles.ADMIN",ignore = true)
//    ApiRoleDTO mapToDTO(ApiRole role);

//    @Named("mapApiRole")
//    ApiRole mapFromDTO(ApiRoleDTO apiRole);
}
