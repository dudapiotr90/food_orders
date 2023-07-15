package pl.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import pl.dudis.foodorders.api.dtos.RoleDTO;
import pl.dudis.foodorders.infrastructure.security.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    @ValueMapping(source = "ADMIN", target =MappingConstants.NULL)
    RoleDTO mapToDTO(Role role);
}
