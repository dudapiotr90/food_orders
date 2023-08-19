package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OrderDetailsDTO;
import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.dtos.OrdersDTO;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class OrderUtils {

    public static Order someOrder1() {
        return Order.builder()
            .orderId(32)
            .orderNumber("asasda12312sdasmlk")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItems())
            .restaurant(RestaurantUtils.someRestaurant1())
            .customer(CustomerUtils.someCustomer())
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
            .restaurant(RestaurantUtils.someRestaurant2())
            .customer(CustomerUtils.someCustomer2())
            .build();
    }

    public static Order someOrder3() {
        return Order.builder()
            .orderNumber("asdfdfgqw123hjmm4vgaasd")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItems())
            .customer(CustomerUtils.someCustomer2())
            .build();
    }

    public static OrderDTO someOrderDTO1() {
        return OrderDTO.builder()
            .orderId(32)
            .orderNumber("asasda12312sdasmlk")
            .build();
        
    }

    public static OrderDTO someOrderDTO2() {
        return OrderDTO.builder()
            .orderNumber("dfsdsdf34gaasagdfdssadf")
            .build();
    }

    public static OrderDTO someOrderDTO3() {
        return OrderDTO.builder()
            .orderNumber("asdfdfgqw123hjmm4vgaasd")
            .receivedDateTime("2023-08-08 06:30:00")
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someSetOfOrderItemsDTO())
            .restaurant(RestaurantUtils.someRestaurantDTO4())
            .customer(CustomerUtils.someCustomerDTO2())
            .build();
    }


    public static OrderDTO someOrderDTO4() {
        return OrderDTO.builder()
            .orderNumber("asdasdf-adsasdf-43223-dfsdfs-sd")
            .receivedDateTime("2021-11-11 22:30:00")
            .customerComment("another comment")
            .realized(true)
            .inProgress(false)
            .orderItems(OrderItemsUtils.someSetOfOrderItemsDTO())
            .restaurant(RestaurantUtils.someRestaurantDTO4())
            .customer(CustomerUtils.someCustomerDTO2())
            .build();
    }

    public static OrderRequestDTO someOrderRequestDTO() {
        return OrderRequestDTO.builder()
            .orderItemsId(OrderItemsUtils.someOrderItemsIds())
            .customerComment("some customer comment")
            .restaurantId(78)
            .build();
    }

    public static List<OrderDetailsDTO> someOrderDetailsList() {
        return List.of(someOrderDetail1(),someOrderDetail2(),someOrderDetail3());
    }

    public static OrderDetailsDTO someOrderDetail1() {
        return OrderDetailsDTO.builder()
            .customerComment("some customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
            .restaurant(RestaurantUtils.someRestaurantForCustomerDTO1())
        .build();
    }

    public static OrderDetailsDTO someOrderDetail2() {
        return OrderDetailsDTO.builder()
            .customerComment("some another customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
            .restaurant(RestaurantUtils.someRestaurantForCustomerDTO2())
            .build();
    }

    public static OrderDetailsDTO someOrderDetail3() {
        return OrderDetailsDTO.builder()
            .customerComment("some other customer comment")
            .orderItems(OrderItemsUtils.someOrderItemsDTO())
            .restaurant(RestaurantUtils.someRestaurantForCustomerDTO3())
            .build();
    }

    public static List<Order> someOrders() {
        return List.of(someOrder1(),someOrder2());
    }
    public static List<OrderDTO> someListOfOrderDTO() {
        return List.of(someOrderDTO1(), someOrderDTO2());
    }

    public static OrdersDTO someOrdersDTO() {
        return OrdersDTO.builder().orders(someListOfOrderDTO()).build();
    }

    public static List<OrderEntity> someOrderEntities() {
        return List.of(someOrderEntity1(),someOrderEntity2());
    }



    public static OrderEntity someOrderEntity1() {
        return OrderEntity.builder()
            .orderId(32)
            .orderNumber("asasda12312sdasmlk")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .cancelTill(OffsetDateTime.of(2023, 8, 8, 6,45, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItemEntities())
            .customer(CustomerUtils.someCustomerEntity1())
            .build();
    }


    public static OrderEntity someOrderEntity2() {
        return OrderEntity.builder()
            .orderNumber("asdfdfgqw123hjmm4vgaasd")
            .receivedDateTime(OffsetDateTime.of(2023, 8, 4, 11, 9, 0, 0, ZoneOffset.UTC))
            .cancelTill(OffsetDateTime.of(2023, 8, 8, 6,25, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment 2")
            .realized(false)
            .inProgress(true)
            .orderItems(OrderItemsUtils.someOrderItemEntities())
            .customer(CustomerUtils.someCustomerEntity2())
            .build();
    }

    public static OrderEntity someOrderEntityToPersist1() {
        return OrderEntity.builder()
            .orderNumber(UUID.randomUUID().toString())
            .receivedDateTime(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .cancelTill(OffsetDateTime.of(2023, 8, 8, 6,45, 0, 0, ZoneOffset.UTC))
            .customerComment("some comment")
            .realized(false)
            .inProgress(false)
            .build();
    }

}
