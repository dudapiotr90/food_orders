package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderDAO.getRestaurantOrders(restaurantId);
    }
}
