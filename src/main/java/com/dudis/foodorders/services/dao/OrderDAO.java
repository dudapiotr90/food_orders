package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getRestaurantOrders(Integer restaurantId);
}
