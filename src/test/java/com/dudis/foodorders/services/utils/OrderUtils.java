package com.dudis.foodorders.services.utils;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.domain.Order;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OrderUtils {

    public static Order someOrder1() {
        return Order.builder()
            .orderNumber("asasda12312sdasmlk")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItems())
            .build();
    }


    public static Order someOrder2() {
        return Order.builder()
            .orderNumber("asdfdfgqw123hjmm4vgaasd")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItems())
            .build();
    }

    public static OrderDTO someOrderDTO() {
        return OrderDTO.builder()
            .orderNumber("asasda12312sdasmlk")
            .build();
        
    }
}
