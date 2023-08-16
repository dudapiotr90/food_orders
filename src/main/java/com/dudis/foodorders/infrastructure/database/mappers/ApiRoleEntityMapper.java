package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleEntityMapper {


    ApiRoleEntity mapToEntity(ApiRole apiRole);

    ApiRole mapFromEntity(ApiRoleEntity apiRoleEntity);
}
