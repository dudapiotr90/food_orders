package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
    uses = {OffsetDateTimeMapper.class, OrderItemMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Named("mapOrderToDTO")
    @Mapping(target = "receivedDateTime", source = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(target = "completedDateTime", source = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "restaurant.orders",target = "restaurant.orders",qualifiedByName = "mapOrders")
    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName = "mapOrderItemsToDTO")
    @Mapping(target = "customer",ignore = true)
    OrderDTO mapToDTO(Order order);


    @Named("mapOrders")
    default Set<OrderDTO> mapOrders(Set<Order> orders) {
        if (Objects.isNull(orders)) {
            return null;
        }
        return orders.stream().map(this::mapToDTO).collect(Collectors.toSet());
    }

}
