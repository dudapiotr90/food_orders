package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.RoleDTO;
import com.dudis.foodorders.infrastructure.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    @ValueMapping(source = "ADMIN", target =MappingConstants.NULL)
    RoleDTO mapToDTO(Role role);
}
