package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Cart;

public class CartUtils {

    public static Cart someCart() {
        return Cart.builder()
            .cartId(6)
            .orderItems(OrderItemsUtils.someOrderItems())
            .build();
    }
}
