package pl.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import pl.dudis.foodorders.api.dtos.ApiRoleDTO;
import pl.dudis.foodorders.infrastructure.security.ApiRole;

@Mapper(componentModel = "spring")
public interface ApiRoleMapper {

    @ValueMapping(source = "ADMIN", target = MappingConstants.NULL)
    ApiRoleDTO map(ApiRole apiRole);
}
