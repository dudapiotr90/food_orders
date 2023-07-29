package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
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
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderJpaRepository.findOrdersByRestaurantId(restaurantId).stream()
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Integer findPendingOrdersForRestaurant(Restaurant restaurantId) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurantId);
        return orderJpaRepository.countByRestaurantIdAndRealized(restaurantEntity,false);
    }
}
