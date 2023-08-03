package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderItemEntityMapper.class})
public interface OrderEntityMapper {
    @Named("mapOrderEntities")
    @Mapping(target = "restaurant", ignore = true)
//    @Mapping(target = "orderDetails", ignore = true)
    Order mapFromEntity(OrderEntity orderEntity);

    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItems")
    @Mapping(source = "restaurant",target = "restaurant")
    OrderEntity mapToEntity(Order order);

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
