package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public interface OrderDAO {
    List<Order> getRestaurantOrders(Integer restaurantId);

    Integer findPendingOrdersForRestaurant(Restaurant restaurantId);

    void issueAnOrder(Order order);
}
