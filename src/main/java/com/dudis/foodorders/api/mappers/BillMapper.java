package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.domain.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {
    @Mapping(target = "customer",ignore = true)
    @Mapping(target = "order",ignore = true)
    BillDTO mapToDTO(Bill bill);
}
