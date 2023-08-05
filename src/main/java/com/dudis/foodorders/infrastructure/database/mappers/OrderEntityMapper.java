package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring"
    ,unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {OrderItemEntityMapper.class, CustomerEntityMapper.class})
public interface OrderEntityMapper {

    @Named("mapOrderFromEntity")
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItemsFromEntity")
    @Mapping(source = "customer",target = "customer",qualifiedByName = "mapCustomerFromEntity")
    Order mapFromEntity(OrderEntity orderEntity);

    @Named("mapOrderToEntity")
    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItems")
    @Mapping(source = "restaurant",target = "restaurant")
    @Mapping(source = "customer",target = "customer",qualifiedByName = "mapCustomerToEntity")
    OrderEntity mapToEntity(Order order);

    @Named("mapOrdersFromEntity")
    default Set<Order> mapOrdersFromEntity(Set<OrderEntity> orders) {
        return orders.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }




}
