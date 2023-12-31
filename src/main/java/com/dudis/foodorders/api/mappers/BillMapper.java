package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.domain.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
    ,uses = {OrderMapper.class,CustomerMapper.class,OffsetDateTimeMapper.class})
public interface BillMapper {
    @Mapping(source = "customer",target = "customer",qualifiedByName = "mapCustomerToDTO")
    @Mapping(target = "owner",ignore = true)
    @Mapping(source = "order",target = "order",qualifiedByName ="mapOrderToDTO" )
    @Mapping(source = "dateTime",target = "dateTime",qualifiedByName ="mapOffsetDateTimeToString" )
    BillDTO mapToDTO(Bill bill);
}
