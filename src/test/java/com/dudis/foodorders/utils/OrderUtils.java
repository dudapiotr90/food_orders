package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OrderDetailsDTO;
import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.domain.Order;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

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

    public static OrderDTO someOrderDTO1() {
        return OrderDTO.builder()
            .orderNumber("asasda12312sdasmlk")
            .build();
        
    }

    public static OrderDTO someOrderDTO2() {
        return OrderDTO.builder()
            .orderNumber("dfsdsdf34gaasagdfdssadf")
            .build();

    }

    public static OrderRequestDTO someOrderRequestDTO() {
        return OrderRequestDTO.builder()
            .orderItemsId(OrderItemsUtils.someOrderItemsIds())
            .customerComment("some customer comment")
            .build();
    }

    public static List<OrderDetailsDTO> someOrderDetailsList() {
        return List.of(someOrderDetail1(),someOrderDetail2(),someOrderDetail3());
    }

    public static OrderDetailsDTO someOrderDetail1() {
        return OrderDetailsDTO.builder()
            .customerComment("some customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
        .build();
    }

    public static OrderDetailsDTO someOrderDetail2() {
        return OrderDetailsDTO.builder()
            .customerComment("some another customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
        .build();
    }

    public static OrderDetailsDTO someOrderDetail3() {
        return OrderDetailsDTO.builder()
            .customerComment("some other customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
        .build();
    }

    public static List<Order> someOrders() {
        return List.of(someOrder1(),someOrder2());
    }
}
