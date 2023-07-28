package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    @Mapping(target = "restaurant",ignore = true)
    @Mapping(target = "orderDetails",ignore = true)
    Order mapFromEntity(OrderEntity orderEntity);

//    @Named("mapOrderItems")
//    default Set<OrderItem> mapOrderItems(Set<OrderItemEntity> orderItems) {
//        return orderItems.stream()
//            .map(this::mapOrderItem)
//            .collect(Collectors.toSet());
//    }

//    @Mapping(target = "order",ignore = true)
//    @Mapping(target = "food",ignore = true)
//    OrderItem mapOrderItem(OrderItemEntity orderItem);
}
