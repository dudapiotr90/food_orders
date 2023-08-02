package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {OffsetDateTimeMapper.class, OrderDetailsMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Named("mapOrderToDTO")
    @Mapping(target = "receivedDateTime", source = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(target = "completedDateTime", source = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(target = "orderDetails",ignore = true)
    @Mapping(source = "restaurant.orders",target = "restaurant.orders",qualifiedByName = "mapOrders")
    OrderDTO mapToDTO(Order order);

    @Named("mapOrderItemsToDTO")
    default Set<OrderItemDTO> mapOrderItemsToDTO(Set<OrderItem> orderItems) {
        return orderItems.stream()
            .map(this::mapOrderItem)
            .collect(Collectors.toSet());
    }

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "cart", ignore = true)
    OrderItemDTO mapOrderItem(OrderItem orderItem);

    @Named("mapOrders")
    default Set<OrderDTO> mapOrders(Set<Order> orders) {
        return orders.stream().map(this::mapToDTO).collect(Collectors.toSet());
    }


}
