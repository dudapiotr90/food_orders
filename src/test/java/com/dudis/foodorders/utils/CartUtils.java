package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;

public class CartUtils {

    public static Cart someCart() {
        return Cart.builder()
            .customer(CustomerUtils.someCustomer())
            .cartId(6)
            .orderItems(OrderItemsUtils.someOrderItems())
            .build();
    }

    public static CartEntity someCartEntity() {
        return CartEntity.builder()
            .customerId(5)
            .cartId(6)
            .orderItems(OrderItemsUtils.someOrderItemEntities())
            .build();
    }
}
