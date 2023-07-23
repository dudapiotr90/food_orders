package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderJpaRepository;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderJpaRepository.findOrdersByRestaurantId(restaurantId).stream()
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }
}
