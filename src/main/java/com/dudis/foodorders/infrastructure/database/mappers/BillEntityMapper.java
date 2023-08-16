package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {OrderEntityMapper.class,CustomerEntityMapper.class,OwnerEntityMapper.class})
public interface BillEntityMapper {

    @Mapping(target = "owner",ignore = true)
    @Mapping(source = "customer",target = "customer",qualifiedByName = "mapCustomerFromEntity")
    @Mapping(source = "order",target = "order",qualifiedByName ="mapOrderFromEntity" )
    Bill mapFromEntity(BillEntity bill);

    @Mapping(source ="owner", target = "owner",qualifiedByName = "mapOwnerToEntity")
    @Mapping(source ="customer",target = "customer",qualifiedByName = "mapCustomerToEntity")
    @Mapping(source ="order",target = "order",qualifiedByName = "mapOrderToEntity")
    BillEntity mapToEntity(Bill bill);
}
