package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderItemEntityMapper.class})
public interface CartEntityMapper {
    @Mapping(target = "orderDetails",ignore = true)
    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName ="mapOrderItemsFromEntity" )
    Cart mapFromEntity(CartEntity cart);

    @Mapping(target = "orderDetails",ignore = true)
    @Mapping(target = "orderItems",ignore = true)
//    @Mapping(target = "customer",ignore = true)
//    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItems")
    CartEntity mapToEntity(Cart cartToSave);
}
