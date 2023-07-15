package pl.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.api.dtos.ApiRoleDTO;
import pl.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import pl.dudis.foodorders.api.dtos.RoleDTO;
import pl.dudis.foodorders.infrastructure.security.ApiRole;
import pl.dudis.foodorders.infrastructure.security.Role;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationRequestMapper {

//    @Mapping(source = "apiRole",target = "apiRole",qualifiedByName = "mapApiRole")
//    @Mapping(target = "apiRole.apiRoleId",ignore = true)
    RegistrationRequest mapFromDTO(RegistrationRequestDTO registrationRequestDTO);

}
