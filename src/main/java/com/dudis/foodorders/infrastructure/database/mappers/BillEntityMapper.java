package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderEntityMapper.class})
public interface BillEntityMapper {

    @Mapping(target = "owner",ignore = true)
    @Mapping(target = "customer",ignore = true)
    @Mapping(source = "order",target = "order",qualifiedByName ="mapOrderEntities" )
    Bill mapFromEntity(BillEntity bill);
}
