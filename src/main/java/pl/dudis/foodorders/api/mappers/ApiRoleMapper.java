package pl.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.api.dtos.ApiRoleDTO;
import pl.dudis.foodorders.infrastructure.security.ApiRole;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleMapper {

//    @Mapping(source = "roles.ADMIN",ignore = true)
//    ApiRoleDTO mapToDTO(ApiRole role);

    @Named("mapApiRole")
    ApiRole mapFromDTO(ApiRoleDTO apiRole);
}
