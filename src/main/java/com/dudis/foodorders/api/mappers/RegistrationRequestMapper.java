package com.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationRequestMapper {

//    @Mapping(source = "apiRole",target = "apiRole",qualifiedByName = "mapApiRole")
//    @Mapping(target = "apiRole.apiRoleId",ignore = true)
    RegistrationRequest mapFromDTO(RegistrationRequestDTO registrationRequestDTO);

}
