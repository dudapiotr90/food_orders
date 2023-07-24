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

//    @Mapping(target = "restaurant",ignore = true)
//    @Mapping(target = "customer",ignore = true)
    @Mapping(target = "receivedDateTime",source = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(target = "completedDateTime",source = "completedDateTime",qualifiedByName = "mapOffsetDateTimeToString")
//    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItemsToDTO")
    @Mapping(target = "orderItems",ignore = true)
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
}
