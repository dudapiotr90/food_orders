package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.domain.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class OrderItemsUtils {
    public static Set<OrderItem> someOrderItems() {
        return Set.of(someOrderItem1(),someOrderItem2());
    }

    public static OrderItem someOrderItem1() {
        return OrderItem.builder()
            .quantity(BigDecimal.valueOf(3))
            .food(FoodUtils.someFood1())
            .build();
    }

    public static OrderItem someOrderItem2() {
        return OrderItem.builder()
            .quantity(BigDecimal.valueOf(5))
            .food(FoodUtils.someFood2())
            .build();
    }

    public static OrderItemDTO someOrderItemDTO1() {
        return OrderItemDTO.builder()
            .orderItemId(56)
            .quantity(BigDecimal.valueOf(44))
            .build();
    }

    public static Set<Integer> someOrderItemsIds() {
        return Set.of(132, 54, 78);
    }

    public static List<OrderItemDTO> someOrderItemsDTO() {
        return List.of(someOrderItemDTO1(), someOrderItemDTO2(), someOrderItemDTO3());
    }

    public static OrderItemDTO someOrderItemDTO2() {
        return OrderItemDTO.builder()
            .orderItemId(78)
            .quantity(BigDecimal.valueOf(312.12))
            .build();
    }
    public static OrderItemDTO someOrderItemDTO3() {
        return OrderItemDTO.builder()
            .orderItemId(213)
            .quantity(BigDecimal.valueOf(845.14))
            .build();
    }
}
