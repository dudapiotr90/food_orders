package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final RestaurantMapper restaurantMapper;

    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderDAO.getRestaurantOrders(restaurantId);
    }

    public Integer countPendingOrdersForRestaurant(Restaurant restaurantId) {
        return orderDAO.findPendingOrdersForRestaurant(restaurantId);
    }

    public void makeAnOrder(List<OrderItem> orderItems, String customerComment, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantMapper.mapFromDTO(restaurantDTO);
        Order order = buildOrderDetails(orderItems, restaurant,customerComment);
        orderDAO.issueAnOrder(order);

    }

    private Order buildOrderDetails(List<OrderItem> orderItems, Restaurant restaurant,String customerComment) {
        return Order.builder()
            .orderNumber(UUID.randomUUID().toString())
            .receivedDateTime(OffsetDateTime.now())
            .customerComment(customerComment)
            .realized(false)
            .orderItems(orderItems)
            .restaurant(restaurant)
            .build();
    }
}
