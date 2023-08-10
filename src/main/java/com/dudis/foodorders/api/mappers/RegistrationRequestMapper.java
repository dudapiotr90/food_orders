package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationRequestMapper {

    RegistrationRequest mapFromDTO(RegistrationRequestDTO registrationRequestDTO);

    RegistrationRequestDTO mapToDTO(RegistrationRequest registrationRequest);


}
