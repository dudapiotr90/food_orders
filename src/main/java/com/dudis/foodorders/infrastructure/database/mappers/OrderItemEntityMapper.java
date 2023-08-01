package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {

    @Mapping(target = "order",ignore = true)
    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "food.menu",ignore = true)
    OrderItemEntity mapToEntity(OrderItem itemToAdd);

    @Named("mapOrderItems")
    default Set<OrderItemEntity> mapOrderItems(Set<OrderItem> orderItems) {
        return orderItems.stream().map(this::mapToEntity).collect(Collectors.toSet());
    }
}
