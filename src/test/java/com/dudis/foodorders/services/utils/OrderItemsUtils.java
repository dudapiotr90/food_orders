package com.dudis.foodorders.services.utils;

import com.dudis.foodorders.domain.OrderItem;

import java.math.BigDecimal;
import java.util.Set;

public class OrderItemsUtils {
    public static Set<OrderItem> someOrderItems() {
        return Set.of(someOrderItem1(),someOrderItem2());
    }

    private static OrderItem someOrderItem1() {
        return OrderItem.builder()
            .quantity(BigDecimal.valueOf(3))
            .food(FoodUtils.someFood1())
            .build();
    }

    private static OrderItem someOrderItem2() {
        return OrderItem.builder()
            .quantity(BigDecimal.valueOf(5))
            .food(FoodUtils.someFood2())
            .build();
    }
}
