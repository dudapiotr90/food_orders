package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = OffsetDateTimeMapper.class)
public interface OrderMapper {
    @Mapping(target = "receivedDateTime",source = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(target = "completedDateTime",source = "completedDateTime",qualifiedByName = "mapOffsetDateTimeToString")
    OrderDTO mapToDTO(Order order);

    @Named("mapOrderItemsToDTO")
    default Set<OrderItemDTO> mapOrderItemsToDTO(Set<OrderItem> orderItems) {
        return orderItems.stream()
            .map(this::mapOrderItem)
            .collect(Collectors.toSet());
    }

    @Mapping(target = "order",ignore = true)
    @Mapping(target = "food",ignore = true)
    OrderItemDTO mapOrderItem(OrderItem orderItem);

    @Named("mapOrders")
    default Set<OrderDTO> mapOrders(Set<Order> orders) {
        return orders.stream().map(this::mapToDTO).collect(Collectors.toSet());
    }


}
