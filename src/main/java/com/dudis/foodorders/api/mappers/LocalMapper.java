package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.LocalDTO;
import com.dudis.foodorders.domain.Local;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalMapper {


    LocalDTO mapToDTO(Local local);
}
