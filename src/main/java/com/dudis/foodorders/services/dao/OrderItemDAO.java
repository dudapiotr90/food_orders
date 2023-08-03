package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;

public interface OrderItemDAO {
    void addItemToCart(Cart cart, OrderItem itemToAdd);

    void updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(Integer orderItemId);

    OrderItem findOrderItemById(Integer id);

    void deleteOrderItemFromCart(OrderItem orderItem, Cart cart);
}
