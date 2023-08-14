package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    List<Order> getRestaurantOrders(Integer restaurantId, boolean isInProgress);

    Integer findPendingOrdersForRestaurant(Restaurant restaurantId);

    Order issueAnOrder(Order order);

    void cancelOrder(Order order);

    List<Order> findCancelableOrders(Integer customerId);

    Optional<Order> findByOrderNumber(String orderNumber);

    void setOrderAsInProgress(Order order);

    void realizeOrder(Order orderNumber);

    Page<Order> getPaginatedRealizedOwnerOrders(List<Integer> restaurantIds, boolean realized, Pageable pageable);

    Restaurant findRestaurantByOrderNumber(String orderNumber);

    Page<Order> getPaginatedRealizedCustomerOrders(Integer customerId, boolean realized, Pageable pageable);
}
