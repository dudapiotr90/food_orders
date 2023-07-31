package com.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleEntityMapper {


    ApiRoleEntity mapToDTO(ApiRole apiRole);

    ApiRole mapFromDTO(ApiRoleEntity apiRoleEntity);
}
